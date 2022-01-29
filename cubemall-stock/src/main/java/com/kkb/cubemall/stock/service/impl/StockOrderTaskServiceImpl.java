package com.kkb.cubemall.stock.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.stock.dao.StockOrderTaskDao;
import com.kkb.cubemall.stock.entity.StockOrderTaskEntity;
import com.kkb.cubemall.stock.service.StockOrderTaskService;


@Service("stockOrderTaskService")
public class StockOrderTaskServiceImpl extends ServiceImpl<StockOrderTaskDao, StockOrderTaskEntity> implements StockOrderTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockOrderTaskEntity> page = this.page(
                new Query<StockOrderTaskEntity>().getPage(params),
                new QueryWrapper<StockOrderTaskEntity>()
        );

        return new PageUtils(page);
    }

}