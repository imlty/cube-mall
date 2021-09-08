package com.kkb.cubemall.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.common.utils.PageUtils;
import com.kkb.common.utils.Query;
import com.kkb.cubemall.product.dao.CategoryBrandDao;
import com.kkb.cubemall.product.entity.CategoryBrandEntity;
import com.kkb.cubemall.product.service.CategoryBrandService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("categoryBrandService")
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandDao, CategoryBrandEntity> implements CategoryBrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandEntity> page = this.page(
                new Query<CategoryBrandEntity>().getPage(params),
                new QueryWrapper<CategoryBrandEntity>()
        );

        return new PageUtils(page);
    }

}
