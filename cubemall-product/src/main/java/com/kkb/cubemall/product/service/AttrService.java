package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.AttrEntity;
import com.kkb.cubemall.product.vo.AttrGroupReationVo;
import com.kkb.cubemall.product.vo.AttrRespVo;
import com.kkb.cubemall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 属性表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-09 14:04:42
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);


    AttrRespVo getAttrInfo(Long id);



    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Integer categoryId, String attrType);

    void updateAttr(AttrVo attrVo);

    List<AttrEntity> getRelationAttr(Long attrGroupId);

    void deleteRalation(AttrGroupReationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId);
}

