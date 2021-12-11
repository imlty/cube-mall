package com.kkb.cubemall.product.service.impl;

import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kkb.cubemall.product.dao.SpuInfoDescDao;
import com.kkb.cubemall.product.entity.SpuInfoDescEntity;
import com.kkb.cubemall.product.service.SpuInfoDescService;


@Service("spuInfoDescService")
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescDao, SpuInfoDescEntity> implements SpuInfoDescService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoDescEntity> page = this.page(
                new Query<SpuInfoDescEntity>().getPage(params),
                new QueryWrapper<SpuInfoDescEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 2.保存spu的描述信息: tb_spu_info_desc
     * @param spuInfoDescEntity
     */
    @Override
    public void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity) {
        this.baseMapper.insert(spuInfoDescEntity);
    }

}