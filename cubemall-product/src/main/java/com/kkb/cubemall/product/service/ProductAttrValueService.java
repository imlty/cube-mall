package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-13 20:26:25
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> collect);
}

