package com.kkb.cubemall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.order.dao.OrderReturnDao;
import com.kkb.cubemall.order.entity.OrderReturnEntity;
import com.kkb.cubemall.order.service.OrderReturnService;


@Service("orderReturnService")
public class OrderReturnServiceImpl extends ServiceImpl<OrderReturnDao, OrderReturnEntity> implements OrderReturnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderReturnEntity> page = this.page(
                new Query<OrderReturnEntity>().getPage(params),
                new QueryWrapper<OrderReturnEntity>()
        );

        return new PageUtils(page);
    }

}