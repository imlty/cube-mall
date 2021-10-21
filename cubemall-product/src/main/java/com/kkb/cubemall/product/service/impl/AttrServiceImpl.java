package com.kkb.cubemall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.product.dao.AttrDao;
import com.kkb.cubemall.product.dao.AttrGroupDao;
import com.kkb.cubemall.product.dao.CategoryDao;
import com.kkb.cubemall.product.entity.AttrAttrgroupRelationEntity;
import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.entity.CategoryEntity;
import com.kkb.cubemall.product.service.AttrAttrgroupRelationService;
import com.kkb.cubemall.product.service.AttrService;
import com.kkb.cubemall.product.service.CategoryService;
import com.kkb.cubemall.product.vo.AttrRespVo;
import com.kkb.cubemall.product.vo.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 在 attr 表中保存基本属性信息，在关联表中保存关联信息
     *
     * @param attrVo
     */
    @Override
    public void saveAttrVo(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);

        // 保存基本数据
        this.save(attrEntity);

        // 保存关联数据
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getId());
        attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
        attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
    }

    /**
     * 获取指定分类下的基本属性
     *
     * @param params
     * @param attrType
     * @param categoryId
     * @return
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, String attrType, Long categoryId) {
        QueryWrapper<AttrEntity> attrEntityQueryWrapper = new QueryWrapper<>();
        if ("base".equalsIgnoreCase(attrType)) {
            attrEntityQueryWrapper.eq("attr_type", 1);
        } else if ("sale".equalsIgnoreCase(attrType)) {
            attrEntityQueryWrapper.eq("attr_type", 0);
        }
        if (categoryId != 0) {
            attrEntityQueryWrapper.eq("category_id", categoryId);
        }
        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)) {
            attrEntityQueryWrapper.eq("id", key).or().like("name", key);
        }
        IPage<AttrEntity> page = this.page(
                // 分页参数
                new Query<AttrEntity>().getPage(params),
                // 查询参数
                attrEntityQueryWrapper
        );
        //------返回之前，获取分组的所属名称和分类名称

        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> attrRespVos = records
                .stream()
                .map(attrEntity -> {
                    AttrRespVo attrRespVo = new AttrRespVo();
                    // 封装基础属性内容
                    BeanUtils.copyProperties(attrEntity, attrRespVo);
                    // 封装属性分组

                    // 1. 查中间表
                    AttrAttrgroupRelationEntity attrgroupRelationEntity = attrAttrgroupRelationService
                            .getBaseMapper()
                            .selectOne(
                                    new QueryWrapper<AttrAttrgroupRelationEntity>()
                                            .eq("attr_id", attrEntity.getId())
                            );
                    // 2. 根据中间表查attr_group
                    AttrGroupEntity attrGroupEntity = null;
                    if (attrgroupRelationEntity != null) {
                        attrGroupEntity = attrGroupDao.selectById(attrgroupRelationEntity.getAttrGroupId());
                        if (attrGroupEntity != null) {
                            attrRespVo.setGroupName(attrGroupEntity.getName());
                        }
                    }
                    // 3. 查询分类
                    if (attrGroupEntity != null) {
                        CategoryEntity categoryEntity = categoryDao.selectById(attrGroupEntity.getCategoryId());
                        if (categoryEntity != null) {
                            attrRespVo.setCategoryName(categoryEntity.getName());
                        }
                    }
                    // 封装分组名称
                    return attrRespVo;
                }).collect(Collectors.toList());

        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(attrRespVos);
        return pageUtils;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public AttrRespVo getAttrInfo(Long id) {
        AttrRespVo attrRespVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(id);
        BeanUtils.copyProperties(attrEntity, attrRespVo);
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationService.getBaseMapper().selectOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id", attrEntity.getId())
        );
        // 设置 attrRespVO names
        if (attrAttrgroupRelationEntity != null) {
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
            if (attrGroupEntity != null) {
                attrRespVo.setAttrGroupId(attrGroupEntity.getId());
                attrRespVo.setGroupName(attrGroupEntity.getName());
                CategoryEntity categoryEntity = categoryDao.selectById(attrGroupEntity.getCategoryId());
                if (categoryEntity != null) {
                    attrRespVo.setCategoryName(categoryEntity.getName());
                }
            }
        }
        // 设置分类目录路径
        Long[] categoryPath = categoryService.findCategoryPath(attrEntity.getCategoryId());
        attrRespVo.setCategoryPath(categoryPath);

        return attrRespVo;
    }

    /**
     * 规格参数的属性修改
     * 【修改包括：attr表 、attrAttrGroupRelation表 、attrAttrGroup表 、 category表】
     *
     * @param attrVo
     */
    @Override
    public void updateAttr(AttrVo attrVo) {
        // 基本属性
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        this.updateById(attrEntity);
        // 修改其他表
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrVo.getId());
        attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());

        Integer count = attrAttrgroupRelationService.getBaseMapper()
                .selectCount(
                        new QueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id", attrVo.getId())
                );
        if (count > 0) {
            attrAttrgroupRelationService.update(
                    attrAttrgroupRelationEntity,
                    new UpdateWrapper<AttrAttrgroupRelationEntity>()
                            .eq("attr_id", attrVo.getId())
            );
        } else {
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }
    }

}