package com.kkb.cubemall.product.service.impl;

import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.service.AttrService;
import com.kkb.cubemall.product.vo.AttrGroupWithAttrsVo;
import com.kkb.cubemall.product.vo.GroupAttrParamVo;
import com.kkb.cubemall.product.vo.SpuAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.product.dao.AttrGroupDao;
import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;

/**
 * @Description: 方法描述
 * @Author: hubin
 * @CreateDate: 2021/5/14 11:22
 * @UpdateUser: hubin
 * @UpdateDate: 2021/5/14 11:22
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
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

    @Override
    public PageUtils queryPage(Map<String, Object> params, Integer categoryId) {

        String key = (String)params.get("key");//模糊搜索关键字
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>(); //查询条件
        if(!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("id",key).or().like("name",key); //ID精确查找已经名称模糊查询
            });
        }

        if(categoryId==0){ //分类如果为0，全部查询
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),wrapper);
            return new PageUtils(page);
        }else{ //根据分类进行过滤
            wrapper.eq("category_id",categoryId);
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),wrapper);
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithattrByCategroyId(Integer categoryId) {
        //根据分类ID查询分类的下的属性分组
        List<AttrGroupEntity> attrGroupEntities =
                this.list(new QueryWrapper<AttrGroupEntity>().eq("category_id",categoryId));
        List<AttrGroupWithAttrsVo> result = attrGroupEntities.stream().map(group->{
            AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group,attrsVo);
            //根据属性分组ID获取相对应的属性
            List<AttrEntity> attrs = attrService.getRelationAttr(attrsVo.getId());
            attrsVo.setAttrs(attrs);
            return attrsVo;
        }).collect(Collectors.toList());
        return result;
    }

    //根据spuID,categoryId 查询 sku分组规格参数属性值
    @Override
    public List<SpuAttrGroupVo> getGroupAttr(Long spuId, Long categoryId) {

        GroupAttrParamVo paramVo = new GroupAttrParamVo();
        paramVo.setSpuId(spuId);
        paramVo.setCategoryId(categoryId);

        List<SpuAttrGroupVo> attrGroupVos =  this.baseMapper.getGroupAttr(paramVo);

        return attrGroupVos;
    }


}
