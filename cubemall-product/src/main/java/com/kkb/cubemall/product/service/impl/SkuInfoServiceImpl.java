package com.kkb.cubemall.product.service.impl;

import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.product.entity.SpuInfoEntity;
import com.kkb.cubemall.product.vo.SpuSaveVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kkb.cubemall.product.dao.SkuInfoDao;
import com.kkb.cubemall.product.entity.SkuInfoEntity;
import com.kkb.cubemall.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 5.1 保存sku的基本信息:tb_sku_info
     * @param skuInfoEntity
     */
    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    /**
     * sku的条件查询
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        //是否携带 key
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w->{
                w.eq("id", key).or().like("sku_name", key);
            });
        }
        //是否携带key
        String categoryId = (String) params.get("categoryId");
        if (!StringUtils.isEmpty(categoryId) && !"0".equalsIgnoreCase(categoryId)){
            wrapper.eq("category_id", categoryId);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id", brandId);
        }

        //是否携带了 价格区间
        String min = (String) params.get("min");
        if (!StringUtils.isEmpty(min)) {
            wrapper.ge("price", min);
        }

        String max = (String) params.get("max");
        if (!StringUtils.isEmpty(max) && !"0".equalsIgnoreCase(max)) {
            wrapper.le("price", max);
        }

        IPage<SkuInfoEntity> page = this.page(new Query<SkuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

}