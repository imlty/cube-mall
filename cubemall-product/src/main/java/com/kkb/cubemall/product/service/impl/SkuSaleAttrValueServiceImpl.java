package com.kkb.cubemall.product.service.impl;

import com.kkb.cubemall.product.vo.SkuItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.product.dao.SkuSaleAttrValueDao;
import com.kkb.cubemall.product.entity.SkuSaleAttrValueEntity;
import com.kkb.cubemall.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @Description: 根据id查询销售属性组合
     * @Author: hubin
     * @CreateDate: 2021/5/15 16:41
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/15 16:41
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public List<SkuItemSaleAttrVo> getSaleAttrs(Long spuId) {

        //List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrs(spuId);
        List<SkuItemSaleAttrVo> saleAttrVos = this.baseMapper.getSaleAttrs(spuId);

        return saleAttrVos;
    }

    /**
     * @Description: 根据skuID查询销售属性
     * @Author: hubin
     * @CreateDate: 2021/5/26 17:42
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 17:42
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public List<String> getSaleAttrsString(Long skuId) {
        List<String> saleAttrsString = this.baseMapper.getSaleAttrsString(skuId);
        return saleAttrsString;
    }


}