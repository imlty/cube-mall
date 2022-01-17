package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品类目
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-06 10:32:36
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<CategoryEntity> listTree();

    void removeNodesByIds(List<Integer> asList);


    Integer[] findCategoryPath(Integer categoryId);
}

