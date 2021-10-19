package com.kkb.cubemall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.product.dao.AttrGroupDao;
import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.service.AttrGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

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
     *
     * @param params
     * @param categoryId
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        if (categoryId == 0) {
            String key = (String) params.get("key");
            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
            // 查询条件： key 【名称】
            if (StringUtils.isNotEmpty(key)) {
                wrapper.and(obj -> obj.eq("id", key).or().like("name", key));
            }
            IPage<AttrGroupEntity> page = this.page(
                    // 分页条件
                    new Query<AttrGroupEntity>().getPage(params),
                    // 查询条件
                    wrapper
            );
            return new PageUtils(page);
        } else {
            // 查询指定分类下的所有属性分组信息 【包括 categoryId 和 parmas.key】
            String key = (String) params.get("key");
            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
            // 查询条件1： categoryId
            // 查询条件2： key 【名称】
            wrapper.eq("category_id", categoryId);
            if (StringUtils.isNotEmpty(key)) {
                wrapper.and(obj -> obj.eq("id", key).or().like("name", key));
            }
            IPage<AttrGroupEntity> page = this.page(
                    // 分页条件
                    new Query<AttrGroupEntity>().getPage(params),
                    // 查询条件
                    wrapper
            );
            return new PageUtils(page);
        }
    }

}