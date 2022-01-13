package com.kkb.cubemall.ware.service.impl;

import com.kkb.cubemall.common.utils.WareConstant;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.ware.entity.PurchaseDetailEntity;
import com.kkb.cubemall.ware.service.PurchaseDetailService;
import com.kkb.cubemall.ware.service.WareSkuService;
import com.kkb.cubemall.ware.vo.MergeVo;
import com.kkb.cubemall.ware.vo.PurchaseDoneVo;
import com.kkb.cubemall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kkb.cubemall.ware.dao.PurchaseDao;
import com.kkb.cubemall.ware.entity.PurchaseEntity;
import com.kkb.cubemall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService purchaseDetailService;

    @Autowired
    WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询未领取的采购单
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1)
        );

        return new PageUtils(page);
    }

    /**
     * 合并采购单
     * @param mergeVo
     */
    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();

        if (purchaseId == null){
            //如果没有默认的采购单,purchaseId为null, 需要新建采购单
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            //采购单的状态为新建状态
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            //得到新的采购单ID
            purchaseId = purchaseEntity.getId();

        }
        //已有采购单, 更新采购项的状态
        List<Long> items = mergeVo.getItems();
        final Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> collect = items.stream().map(item -> {
            //新建采购项的实体类
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(item); //设置 采购项的ID
            purchaseDetailEntity.setPurchaseId(finalPurchaseId); //设置采购单的ID
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        //批量更新采购项的值
        purchaseDetailService.updateBatchById(collect);

        //在合并完采购单后, 希望新的采购单实现是正确更新的
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(finalPurchaseId); //设置采购单的ID
        purchaseEntity.setUpdateTime(new Date()); //设置采购单的 更新时间
        this.updateById(purchaseEntity);


    }

    /**
     * 领取采购单
     * @param ids 采购员勾选的 采购单ID
     */
    @Transactional
    @Override
    public void received(List<Long> ids) {
        //1.确认当前采购单是 新建 或者 已分配状态
        List<PurchaseEntity> collect = ids.stream().map(id -> {
            PurchaseEntity purchaseEntity = this.getById(id);
            return purchaseEntity;
        }).filter(item->{
            //过滤出 当前采购单是 新建 或者 已分配状态
            if (item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() ||
                item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()){
                return true;
            }
            return false;
        }).map(item->{
            //更新采购单状态
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            //更新日期的值
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        //2.调用持久层 改变采购单的状态(更新数据库)
        this.updateBatchById(collect);

        //3.改变采购项(采购需求)的状态
        collect.forEach(item->{
            //获取每一个采购单中 关联的采购项
            List<PurchaseDetailEntity> entities = purchaseDetailService.listDetailByPurchaseId(item.getId());

            //设置要更新的采购项( 设置采购项id, 设置采购项的状态 )
            List<PurchaseDetailEntity> detailEntities = entities.stream().map(entity -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(entity.getId());
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                return purchaseDetailEntity;
            }).collect(Collectors.toList());

            //更新采购项 (更新数据库)
            purchaseDetailService.updateBatchById(detailEntities);
        });
    }

    /**
     * 完成采购
     * @param purchaseDoneVo
     */
    @Transactional
    @Override
    public void done(PurchaseDoneVo purchaseDoneVo) {
        // 改变采购单中的每一个采购项的状态
        List<PurchaseItemDoneVo> items = purchaseDoneVo.getItems();
        /**
         *  记录当前采购单是否正常,没有出现采购失败的情况
         *  true: 没有出现采购失败的采购项
         *  false: 出现了采购失败的采购项
         */
        Boolean flag = true;

        List<PurchaseDetailEntity> list = new ArrayList<>();

        for (PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(item.getItemId());

            //判断当前遍历的采购项 状态是否为 3===已完成, 进行将采购成功的商品进行入库
            if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.FINISH.getCode()){
                //设置当前采购项的状态为 已完成
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                //将采购成功的商品进行入库
                PurchaseDetailEntity detailEntity = purchaseDetailService.getById(item.getItemId());
                //将 指定仓库,指定sku的 库存数量 增加
                wareSkuService.addStock(detailEntity.getSkuId(), detailEntity.getWareId(), detailEntity.getSkuNum());
            }

            //判断当前遍历的采购项 状态是否为 4===采购失败, flag=false,表示当前采购单有有采购失败的商品
            if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()){
                //设置当前采购项的状态为 采购失败
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode());
                flag = false;
            }

            list.add(purchaseDetailEntity);
        }
        //批量更新采购项的状态
        purchaseDetailService.updateBatchById(list);

        //改变采购单的状态
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseDoneVo.getId());
        if (flag){
            //true: 没有出现采购失败的采购项
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.FINISH.getCode());
        } else {
            //false: 出现了采购失败的采购项
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        }
        //更新采购单的时间
        purchaseEntity.setUpdateTime(new Date());
        // 更新采购单
        this.updateById(purchaseEntity);
    }

}