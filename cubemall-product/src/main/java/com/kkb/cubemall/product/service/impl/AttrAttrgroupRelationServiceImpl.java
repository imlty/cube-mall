package com.kkb.cubemall.product.service.impl;

import com.kkb.cubemall.product.vo.AttrGroupReationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.product.dao.AttrAttrgroupRelationDao;
import com.kkb.cubemall.product.entity.AttrAttrgroupRelationEntity;
import com.kkb.cubemall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 关联属性id与属性分组的关系
     * @param vos
     */
    @Override
    public void saveBatch(List<AttrGroupReationVo> vos) {
        List<AttrAttrgroupRelationEntity> collect = vos.stream().map(item->{
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item,relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }


}
