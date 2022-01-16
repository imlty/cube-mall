package com.kkb.cubemall.auth.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.auth.dao.OrderHistoryDao;
import com.kkb.cubemall.auth.entity.OrderHistoryEntity;
import com.kkb.cubemall.auth.service.OrderHistoryService;


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