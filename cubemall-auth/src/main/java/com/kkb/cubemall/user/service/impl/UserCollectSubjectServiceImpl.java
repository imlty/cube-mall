package com.kkb.cubemall.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.user.dao.UserCollectSubjectDao;
import com.kkb.cubemall.user.entity.UserCollectSubjectEntity;
import com.kkb.cubemall.user.service.UserCollectSubjectService;


@Service("userCollectSubjectService")
public class UserCollectSubjectServiceImpl extends ServiceImpl<UserCollectSubjectDao, UserCollectSubjectEntity> implements UserCollectSubjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCollectSubjectEntity> page = this.page(
                new Query<UserCollectSubjectEntity>().getPage(params),
                new QueryWrapper<UserCollectSubjectEntity>()
        );

        return new PageUtils(page);
    }

}