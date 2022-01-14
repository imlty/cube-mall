package com.kkb.cubemall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.order.dao.UserCollectSpuDao;
import com.kkb.cubemall.order.entity.UserCollectSpuEntity;
import com.kkb.cubemall.order.service.UserCollectSpuService;


@Service("userCollectSpuService")
public class UserCollectSpuServiceImpl extends ServiceImpl<UserCollectSpuDao, UserCollectSpuEntity> implements UserCollectSpuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCollectSpuEntity> page = this.page(
                new Query<UserCollectSpuEntity>().getPage(params),
                new QueryWrapper<UserCollectSpuEntity>()
        );

        return new PageUtils(page);
    }

}