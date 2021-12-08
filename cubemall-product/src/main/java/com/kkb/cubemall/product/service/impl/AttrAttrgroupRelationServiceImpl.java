package com.kkb.cubemall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.product.dao.AttrAttrgroupRelationDao;
import com.kkb.cubemall.product.entity.AttrAttrgroupRelationEntity;
import com.kkb.cubemall.product.service.AttrAttrgroupRelationService;
import com.kkb.cubemall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteBatch(List<AttrAttrgroupRelationEntity> relationEntities) {
        attrAttrgroupRelationDao.deleteBatch(relationEntities);
    }

    /**
     * 添加属性于属性分组的关联关系
     *
     * @param vos
     */
    @Override
    public void saveBatch(List<AttrGroupRelationVo> vos) {
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = vos.stream().map(attrGroupRelationVo -> {
            AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(attrGroupRelationVo, entity);
            return entity;
        }).collect(Collectors.toList());
        this.saveBatch(attrAttrgroupRelationEntities);
    }

}