package com.kkb.cubemall.stock.service.impl;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.stock.web.vo.StockSkuVo;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.stock.dao.StockSkuDao;
import com.kkb.cubemall.stock.entity.StockSkuEntity;
import com.kkb.cubemall.stock.service.StockSkuService;


@Service("stockSkuService")
public class StockSkuServiceImpl extends ServiceImpl<StockSkuDao, StockSkuEntity> implements StockSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockSkuEntity> page = this.page(
                new Query<StockSkuEntity>().getPage(params),
                new QueryWrapper<StockSkuEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @Description: 根据skuID查询数据信息
     * @Author: hubin
     * @CreateDate: 2021/6/1 15:37
     * @UpdateUser: hubin
     * @UpdateDate: 2021/6/1 15:37
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public R getSkuStock(List<Long> skuIds) {

        // 创建一个集合，封装对象
        // stream
        List<StockSkuVo> stockSkuList =  skuIds.stream().map(skuId -> {
            // 根据skuID查询库存信息
            StockSkuEntity stockSkuEntity = this.baseMapper.selectOne(new QueryWrapper<StockSkuEntity>().eq("sku_id", skuId));
            // 获取库存数量
            Integer stock = stockSkuEntity.getStock();

            // 创建封装数据对象
            StockSkuVo stockSkuVo = new StockSkuVo();
            stockSkuVo.setSkuId(skuId);
            // 是否有库存
            stockSkuVo.setHasStock(stock == null?false:stock>0);
            return stockSkuVo;
        }).collect(Collectors.toList());

        return R.ok().setData(stockSkuList);
    }

}