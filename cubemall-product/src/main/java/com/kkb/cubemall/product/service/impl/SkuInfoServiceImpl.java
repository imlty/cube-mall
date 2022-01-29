package com.kkb.cubemall.product.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.entity.SkuImagesEntity;
import com.kkb.cubemall.product.entity.SpuInfoDescEntity;
import com.kkb.cubemall.product.feign.SeckillFeignService;
import com.kkb.cubemall.product.service.*;
import com.kkb.cubemall.product.vo.SeckillSkuVo;
import com.kkb.cubemall.product.vo.SkuItemSaleAttrVo;
import com.kkb.cubemall.product.vo.SkuItemVo;
import com.kkb.cubemall.product.vo.SpuAttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.product.dao.SkuInfoDao;
import com.kkb.cubemall.product.entity.SkuInfoEntity;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {


    @Autowired
    private SkuImagesService skuImagesService;

    //注入销售属性组合服务
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    //注入spu描述服务
    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private ThreadPoolExecutor executor;


    @Autowired
    private SeckillFeignService seckillFeignService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    /**
     * @Description: 商品详情页数据加载接口
     * @Author: hubin
     * @CreateDate: 2021/5/15 16:29
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/15 16:29
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public SkuItemVo skuItem(Long skuId) throws ExecutionException, InterruptedException {

        // 新建一个包装类对象
        SkuItemVo itemVo = new SkuItemVo();

        /*1、sku基本信息
        2、sku图片信息（多个图片）
        3、spu的销售属性
        4、spu的描述信息
        5、sku分组规格参数属性值*/

        // 开启异步编排实现，提升服务性能
        // 1、根据skuId 查询 sku基本信息
        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfoEntity skuInfoEntity = this.getById(skuId);
            itemVo.setInfo(skuInfoEntity);
            return skuInfoEntity;
        }, executor);


        // 2、根据skuId查询sku图片信息（多个图片），skuId是外键
        CompletableFuture<Void> imagesFuture = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> imageList = skuImagesService.list(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skuId));
            itemVo.setImages(imageList);
        }, executor);


        //3、根据spuID获取spu的销售属性

        CompletableFuture<Void> salesFuture = infoFuture.thenAcceptAsync((res) -> {
            // 获取sku与之对应的spuId
            Long spuId = res.getSpuId();
            List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrs(spuId);
            if(saleAttrVos.size()>0 && saleAttrVos!=null){

                itemVo.setAttrSales(saleAttrVos);
            }
        }, executor);


        //4、根据spuId查询spu的描述信息
        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
            // 获取sku与之对应的spuId
            Long spuId = res.getSpuId();
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getOne(new QueryWrapper<SpuInfoDescEntity>()
                                                .eq("spu_id",spuId));
            if(spuInfoDescEntity!=null){

                itemVo.setDesc(spuInfoDescEntity);
            }
        }, executor);

        //5、根据spuID,categoryId 查询 sku分组规格参数属性值


        CompletableFuture<Void> groupFuture = infoFuture.thenAcceptAsync((res) -> {
            // 获取sku与之对应的spuId
            Long spuId = res.getSpuId();

            // 获取分类id
            Long categoryId = res.getCategoryId();
            List<SpuAttrGroupVo> attrGroupVos = attrGroupService.getGroupAttr(spuId,categoryId);
            if(attrGroupVos.size()>0){

                itemVo.setAttrGroups(attrGroupVos);
            }

        }, executor);

        //6、根据SkuID查询商品秒杀详情
        CompletableFuture<Void> seckillFuture = infoFuture.thenAcceptAsync((res) -> {
            //查询sku的秒杀详情
            R seckSkuInfo = seckillFeignService.getSeckillSkuRedisTo(skuId);
            if(seckSkuInfo.getCode()==0){
                SeckillSkuVo seckillSkuVo = seckSkuInfo.getData("data",new TypeReference<SeckillSkuVo>(){});
                if(seckillSkuVo!=null){
                    long currentTime = System.currentTimeMillis();
                    if(seckillSkuVo.getEndTime()>currentTime){
                        itemVo.setSeckillSkuVo(seckillSkuVo);
                    }
                }
            }

        }, executor);


        // 等待所有的任务完成后，才能返回结果

            CompletableFuture.allOf(infoFuture,imagesFuture,salesFuture,descFuture,groupFuture,seckillFuture).get();



        return itemVo;
    }


}
