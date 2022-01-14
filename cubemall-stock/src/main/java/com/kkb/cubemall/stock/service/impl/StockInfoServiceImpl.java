package com.kkb.cubemall.stock.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.stock.dao.StockInfoDao;
import com.kkb.cubemall.stock.entity.StockInfoEntity;
import com.kkb.cubemall.stock.service.StockInfoService;


@Service("stockInfoService")
public class StockInfoServiceImpl extends ServiceImpl<StockInfoDao, StockInfoEntity> implements StockInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockInfoEntity> page = this.page(
                new Query<StockInfoEntity>().getPage(params),
                new QueryWrapper<StockInfoEntity>()
        );

        return new PageUtils(page);
    }

}