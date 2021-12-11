package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.vo.AttrGroupRelationVo;
import com.kkb.cubemall.product.vo.AttrRespVo;
import com.kkb.cubemall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 属性表
 *
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attrVo);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long categoryId, String attrType);

    AttrRespVo getAttrInfo(Long id);

    void updateAttr(AttrVo attrVo);

    List<AttrEntity> getRelationAttr(Long attrGroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId);
}

