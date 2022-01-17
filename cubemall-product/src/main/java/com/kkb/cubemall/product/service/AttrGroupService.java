package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.kkb.cubemall.product.vo.AttrGroupWithAttrsVo;
import com.kkb.cubemall.product.vo.SpuAttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组表
 *
 * @author hubin
 * @email ithubin@163.com
 * @date 2021-05-14
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Integer categoryId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithattrByCategroyId(Integer categoryId);

    //5、根据spuID,categoryId 查询 sku分组规格参数属性值
    List<SpuAttrGroupVo> getGroupAttr(Long spuId, Long categoryId);
}

