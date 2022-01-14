package com.kkb.cubemall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.order.dao.OrderHistoryDao;
import com.kkb.cubemall.order.entity.OrderHistoryEntity;
import com.kkb.cubemall.order.service.OrderHistoryService;


@Service("orderHistoryService")
public class OrderHistoryServiceImpl extends ServiceImpl<OrderHistoryDao, OrderHistoryEntity> implements OrderHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderHistoryEntity> page = this.page(
                new Query<OrderHistoryEntity>().getPage(params),
                new QueryWrapper<OrderHistoryEntity>()
        );

        return new PageUtils(page);
    }

}