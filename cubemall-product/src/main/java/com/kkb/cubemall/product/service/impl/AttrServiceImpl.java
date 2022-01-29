package com.kkb.cubemall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kkb.cubemall.common.constant.ProductConstant;
import com.kkb.cubemall.product.dao.AttrAttrgroupRelationDao;
import com.kkb.cubemall.product.dao.AttrGroupDao;
import com.kkb.cubemall.product.dao.CategoryDao;
import com.kkb.cubemall.product.entity.AttrAttrgroupRelationEntity;

import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.entity.CategoryEntity;
import com.kkb.cubemall.product.service.CategoryService;
import com.kkb.cubemall.product.vo.AttrGroupReationVo;
import com.kkb.cubemall.product.vo.AttrRespVo;
import com.kkb.cubemall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.product.dao.AttrDao;
import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


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

    @Override
    public AttrRespVo getAttrInfo(Long id) {

        AttrRespVo attrRespVo = new AttrRespVo();
        //根据数据库查询
        AttrEntity attrEntity = this.getById(id);
        BeanUtils.copyProperties(attrEntity,attrRespVo);
        if(attrEntity.getAttrType()==1){
            //获取分组名称，通过关联表
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity =
                    attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getId()));
            if(attrAttrgroupRelationEntity!=null&&attrAttrgroupRelationEntity.getAttrGroupId()!=null){
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                //设置分组名称
                attrRespVo.setGroupName(attrGroupEntity.getName());
                //设置分组ID
                attrRespVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
            }
        }

        Integer categoryId = attrEntity.getCategoryId();
        Integer[] categoryPath = categoryService.findCategoryPath(categoryId);
        attrRespVo.setCategoryPath(categoryPath);

        //获取分类名称
        CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCategoryId());
        if(categoryEntity!=null){
            attrRespVo.setCategoryName(categoryEntity.getName());
        }
        return attrRespVo;
    }


    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.save(attrEntity);
        if(attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //保存attrgroupId
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId()); //属性分组ID
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getId()); //属性ID
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }

    }

    //service关联分类名称、分组名称

    /**
     *
     * @param params
     * @param categoryId 分类ID
     * @param attrType
     * @return
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Integer categoryId, String attrType) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(attrType)? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() :ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());//基本属性或销售属性
        if(categoryId!=0){ //如果等于0，查询全部
            queryWrapper.eq("category_id",categoryId);
        }
        String key = (String)params.get("key");
        if(!StringUtils.isEmpty(key)){
            queryWrapper.and((wrapper) ->{
                wrapper.eq("id",key).or().like("name",key);
            } );
        }

        IPage<AttrEntity> page= this.page(new Query<AttrEntity>().getPage(params),queryWrapper);
        PageUtils pageUtils = new PageUtils(page);
        //以下是获取分类名称、分组名称
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity)->{

            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity,attrRespVo);

            //获取分组名称，通过关联表
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity =
                    attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getId()));
            if(attrAttrgroupRelationEntity!=null&&attrAttrgroupRelationEntity.getAttrGroupId()!=null){
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                //设置分组名称
                attrRespVo.setGroupName(attrGroupEntity.getName());
            }
            //获取分类名称
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCategoryId());
            if(categoryEntity!=null){
                attrRespVo.setCategoryName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        this.updateById(attrEntity);
        if(attrVo.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){ //判断基本属性添加分组，销售属性不添加分组
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity =
                    new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrVo.getId());
            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());

            //判断当前关联关系是新增还是修改
            Integer count = attrAttrgroupRelationDao.
                    selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrVo.getId()));
            if(count>0){ //修改
                attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity,
                        new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrVo.getId()));
            }else {
                attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
            }
        }
    }

    /**
     * 根据属性分组id获取属性列表
     * @param attrGroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        //获取属性分组及属性关联关系
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id",attrGroupId)
        );
        List<Long> attrids = entities.stream().map(attr->{
            return attr.getAttrId();
        }).collect(Collectors.toList());

        if(attrids==null||attrids.size()<=0){
            return null;
        }
        Collection<AttrEntity> attrEntities = this.listByIds(attrids);
        return (List<AttrEntity>)attrEntities;
    }

    /**
     * 删除属性分组及属性对应关系，根据属性id及属性分组id 的集合
     * @param vos
     */
    @Override
    public void deleteRalation(AttrGroupReationVo[] vos) {
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map(item->{
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item,relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationDao.deleteBatchRelation(entities);
    }

    /**
     * 根据属性分组id，关联哪些可以关联的属性
     * @param params
     * @param attrGroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId) {
        //1 当前分组只能关联自己所属分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
        Integer categoryId=  attrGroupEntity.getCategoryId();
        //2 当前分组只能关联别的分组没有引用的属性
        //2.1 获取当前分类下的其他分组
        List<AttrGroupEntity> group = attrGroupDao.selectList(
                new QueryWrapper<AttrGroupEntity>().eq("category_id",categoryId)
        );
        List<Long> collect = group.stream().map(item->{
            return item.getId();
        }).collect(Collectors.toList());
        //2.2 这些分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id",collect)
        );
        List<Long> attrIds = groupId.stream().map(item->{
            return item.getAttrId();
        }).collect(Collectors.toList());
        //2.3 从当前分类的所有所有属性移除这些属性
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("category_id",categoryId).
                eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());

        if(attrIds!=null&&attrIds.size()>0){
            wrapper.notIn("id",attrIds);
        }
        String key = (String)params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and(w->{
                w.eq("id",key).or().like("name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params),wrapper);
        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }


}
