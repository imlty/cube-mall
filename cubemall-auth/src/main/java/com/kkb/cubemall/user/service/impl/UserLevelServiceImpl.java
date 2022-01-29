package com.kkb.cubemall.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.user.dao.UserLevelDao;
import com.kkb.cubemall.user.entity.UserLevelEntity;
import com.kkb.cubemall.user.service.UserLevelService;


@Service("userLevelService")
public class UserLevelServiceImpl extends ServiceImpl<UserLevelDao, UserLevelEntity> implements UserLevelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserLevelEntity> page = this.page(
                new Query<UserLevelEntity>().getPage(params),
                new QueryWrapper<UserLevelEntity>()
        );

        return new PageUtils(page);
    }

}