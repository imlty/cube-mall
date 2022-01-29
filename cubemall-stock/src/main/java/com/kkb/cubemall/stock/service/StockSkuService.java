package com.kkb.cubemall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.stock.entity.StockSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:09
 */
public interface StockSkuService extends IService<StockSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description: 根据skuID查询数据信息
     * @Author: hubin
     * @CreateDate: 2021/6/1 15:37
     * @UpdateUser: hubin
     * @UpdateDate: 2021/6/1 15:37
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    R getSkuStock(List<Long> skuIds);
}

