package com.kkb.cubemall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.product.dao.AttrAttrgroupRelationDao;
import com.kkb.cubemall.product.dao.AttrGroupDao;
import com.kkb.cubemall.product.dao.CategoryDao;
import com.kkb.cubemall.product.entity.AttrAttrgroupRelationEntity;
import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.entity.CategoryEntity;
import com.kkb.cubemall.product.service.CategoryService;
import com.kkb.cubemall.product.vo.AttrGroupRelationVo;
import com.kkb.cubemall.product.vo.AttrRespVo;
import com.kkb.cubemall.product.vo.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kkb.cubemall.product.dao.AttrDao;
import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.service.AttrService;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;
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
     * 在attr表中保存基本属性信息,然后在关联表中保存关联信息
     * @param attrVo
     */
    @Override
    public void saveAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);

        //保存基本属性数据
        this.save(attrEntity);

        //保存关联数据
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getId());
        attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());

        attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
    }

    /**
     * 获取指定分类的所有基本属性列表
     * @param params
     * @param categoryId
     * @param attrType
     * @return
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long categoryId, String attrType) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        //拼接查询的attrtype条件
        if("base".equals(attrType)){
            queryWrapper.eq("attr_type", 1);
        } else if("sale".equals(attrType)){
            queryWrapper.eq("attr_type", 0);
        }
        //拼接分类id
        if (categoryId != 0) {
            queryWrapper.eq("category_id", categoryId);
        }
        //拼接关键字查询 key
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)){
            queryWrapper.and(wrapper -> {
               wrapper.eq("id", key).or().like("name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        //---------获取所属分类名称-----获取所属分组名称---------------
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            //1.封装基础属性内容
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            //2.封装属性分组名称
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getId())
            );
            //根据attrAttrgroupRelationEntity 获取attrGroup
            if (attrAttrgroupRelationEntity != null) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    attrRespVo.setGroupName(attrGroupEntity.getName());
                }
            }
            //3.封装分组名称
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCategoryId());
            if (categoryEntity != null) {
                attrRespVo.setCategoryName(categoryEntity.getName());
            }

            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long id) {
        AttrRespVo attrRespVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(id);
        //attrEntity中的基本属性 拷贝到  attrRespVo
        BeanUtils.copyProperties(attrEntity, attrRespVo);
        //1.设置分组信息
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getId())
        );
        if (attrAttrgroupRelationEntity != null) {
            attrRespVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
            if (attrGroupEntity != null) {
                attrRespVo.setGroupName(attrGroupEntity.getName());
            }
        }
        //2.设置分类路径
        Long[] categoryPath = categoryService.findCategoryPath(attrEntity.getCategoryId());
        attrRespVo.setCategoryPath(categoryPath);
        //3.设置分类路径
        CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCategoryId());
        if (categoryEntity != null) {
            attrRespVo.setCategoryName(categoryEntity.getName());
        }

        return attrRespVo;
    }

    /**
     * 基本属性的修改--提交操作
     * @param attrVo
     */
    @Override
    public void updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        //基本属性的对拷
        BeanUtils.copyProperties(attrVo, attrEntity);
        this.updateById(attrEntity);

        //修改属性分组关联
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrVo.getId());
        attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());

        Integer count = attrAttrgroupRelationDao.selectCount(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVo.getId())
        );

        if(count > 0){
            attrAttrgroupRelationDao.update(
                    attrAttrgroupRelationEntity,
                    new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVo.getId())
            );
        } else {
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }
    }

    /**
     * 获取属性分组所关联的所有属性
     * @param attrGroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        //获取属性分组及属性关联关系
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId)
        );
        //获取每个属性关联对象中的属性id
        List<Long> attrIds = entities.stream().map(attrAttrgroupRelationEntity -> {
            return attrAttrgroupRelationEntity.getAttrId();
        }).collect(Collectors.toList());

        //通过属性id 获取对于的 AttrEntity对象
        if (attrIds == null || attrIds.size() == 0) {
            return null;
        }
        List<AttrEntity> attrEntities = this.listByIds(attrIds);
        return attrEntities;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        List<AttrAttrgroupRelationEntity> relationEntities = Arrays.asList(vos).stream().map(attrGroupRelationVo -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();

            BeanUtils.copyProperties(attrGroupRelationVo, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        //批量移除属性分组和 属性的关联关系
        attrAttrgroupRelationDao.deleteBatch(relationEntities);
    }

    /**
     * 获取属性分组中, 当前分类下没有进行关联的所有属性
     * @param params
     * @param attrGroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId) {

        //1.获取当前分类下,所有分组已经关联的属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
        //1.1 获取当前属性分组所对应的 分类
        Integer categoryId = attrGroupEntity.getCategoryId();
        //1.2 获取当前分类下 所关联的所有属性分组
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(
                new QueryWrapper<AttrGroupEntity>().eq("category_id", categoryId)
        );
        //获取当前分类下, 所有分组的ID
        List<Long> groupIds = attrGroupEntities.stream().map(attrGroup -> {
            return attrGroup.getId();
        }).collect(Collectors.toList());
        //从中间表 attr_attrgroup_relation中 获取 groupIds 所对应的属性
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds)
        );
        // attrIds 是 当前分类下, 所有属性分组已经关联的 属性集合
        List<Long> attrIds = entities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());


        //2.获取当前分类下所有属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId).eq("attr_type", 1);

        //3.然后移除已经关联的内容,剩下的就是未关联的属性
        if (attrIds != null &&attrIds.size() > 0) {
            queryWrapper.notIn("id", attrIds);
        }

        String key = (String) params.get("key");
        if (StringUtils.isEmpty(key)) {
            queryWrapper.and(wrapper -> {
                wrapper.eq("id", key).or().like("name", key);
            });
        }

        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        return new PageUtils(page);
    }

}