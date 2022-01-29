package com.kkb.cubemall.stock.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.stock.dao.StockOrderTaskDetailDao;
import com.kkb.cubemall.stock.entity.StockOrderTaskDetailEntity;
import com.kkb.cubemall.stock.service.StockOrderTaskDetailService;


@Service("stockOrderTaskDetailService")
public class StockOrderTaskDetailServiceImpl extends ServiceImpl<StockOrderTaskDetailDao, StockOrderTaskDetailEntity> implements StockOrderTaskDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockOrderTaskDetailEntity> page = this.page(
                new Query<StockOrderTaskDetailEntity>().getPage(params),
                new QueryWrapper<StockOrderTaskDetailEntity>()
        );

        return new PageUtils(page);
    }

}