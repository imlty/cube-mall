package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.SkuSaleAttrValueEntity;
import com.kkb.cubemall.product.vo.SkuItemSaleAttrVo;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性值
 * @author hubin
 * @email ithubin@163.com
 * @date 2021-05-14
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description: 根据spuid查询销售属性组合
     * @Author: hubin
     * @CreateDate: 2021/5/15 16:41
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/15 16:41
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    List<SkuItemSaleAttrVo> getSaleAttrs(Long spuId);

    /**
     * @Description: 根据skuID查询销售属性
     * @Author: hubin
     * @CreateDate: 2021/5/26 17:42
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 17:42
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    public List<String> getSaleAttrsString(Long skuId);
}

