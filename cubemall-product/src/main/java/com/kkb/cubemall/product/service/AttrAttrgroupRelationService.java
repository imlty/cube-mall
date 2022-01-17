package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.AttrAttrgroupRelationEntity;
import com.kkb.cubemall.product.vo.AttrGroupReationVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组关联表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-09 14:04:42
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatch(List<AttrGroupReationVo> vos);
}

