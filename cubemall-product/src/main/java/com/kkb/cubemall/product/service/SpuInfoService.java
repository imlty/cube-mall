package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.entity.SpuInfoEntity;
import com.kkb.cubemall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-13 20:26:25
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBeaseSpuInfo(SpuInfoEntity infoEntity);

    void saveSpuInfo(SpuSaveVo vo);

    R putOnSale(Long spuId) throws Exception;
}

