package com.kkb.cubemall.product.service.impl;

import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.service.AttrService;
import com.kkb.cubemall.product.vo.AttrGroupWithAttrsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kkb.cubemall.product.dao.AttrGroupDao;
import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取指定分类下的所有属性分组列表
     * @param params
     * @param categoryId
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {

        if(categoryId == 0){
            //没有点击任何的分类, 查询所有属性分组信息
            IPage<AttrGroupEntity> page = this.page(
                    //分页条件
                    new Query<AttrGroupEntity>().getPage(params),
                    //查询条件(无)
                    new QueryWrapper<AttrGroupEntity>()
            );

            return new PageUtils(page);
        } else {
            //查询指定分类下的所有属性分组信息
            //SELECT * FROM tb_attr_group WHERE category_id=? AND (id=key or name like %key%)\]
            QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
            //查询条件1:  category_id=?
            if (categoryId != 0){
                queryWrapper.eq("category_id", categoryId);
            }
            //查询条件2:  AND (id=key or name like %key%)
            String key = (String) params.get("key");
            if (!StringUtils.isEmpty(key)){
                queryWrapper.and(qw->{
                    qw.eq("id", key).or().like("name", key);
                });
            }

            IPage<AttrGroupEntity> page = this.page(
                    //分页条件
                    new Query<AttrGroupEntity>().getPage(params),
                    //查询条件(queryWrapper)
                    queryWrapper
            );
            return new PageUtils(page);
        }
    }

    /**
     * 获取分类下的所有分组&关联属性
     * @param categoryId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCategoryId(Long categoryId) {
        //1.查询该分类下所有的分组信息
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("category_id", categoryId));
        //2.查询出所有属性
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(attrGroupEntity -> {
            AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
            //属性对拷
            BeanUtils.copyProperties(attrGroupEntity, attrsVo);
            //设置关联的属性
            List<AttrEntity> relationAttr = attrService.getRelationAttr(attrsVo.getId());
            attrsVo.setAttrs(relationAttr);

            return attrsVo;
        }).collect(Collectors.toList());

        return collect;
    }

}