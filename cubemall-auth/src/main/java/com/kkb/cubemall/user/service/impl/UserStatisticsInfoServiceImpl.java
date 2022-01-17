package com.kkb.cubemall.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.user.dao.UserStatisticsInfoDao;
import com.kkb.cubemall.user.entity.UserStatisticsInfoEntity;
import com.kkb.cubemall.user.service.UserStatisticsInfoService;


@Service("userStatisticsInfoService")
public class UserStatisticsInfoServiceImpl extends ServiceImpl<UserStatisticsInfoDao, UserStatisticsInfoEntity> implements UserStatisticsInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserStatisticsInfoEntity> page = this.page(
                new Query<UserStatisticsInfoEntity>().getPage(params),
                new QueryWrapper<UserStatisticsInfoEntity>()
        );

        return new PageUtils(page);
    }

}