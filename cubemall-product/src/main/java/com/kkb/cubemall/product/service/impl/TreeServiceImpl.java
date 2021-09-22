package com.kkb.cubemall.product.service.impl;

import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kkb.cubemall.product.dao.TreeDao;
import com.kkb.cubemall.product.entity.TreeEntity;
import com.kkb.cubemall.product.service.TreeService;


@Service("treeService")
public class TreeServiceImpl extends ServiceImpl<TreeDao, TreeEntity> implements TreeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TreeEntity> page = this.page(
                new Query<TreeEntity>().getPage(params),
                new QueryWrapper<TreeEntity>()
        );

        return new PageUtils(page);
    }

}