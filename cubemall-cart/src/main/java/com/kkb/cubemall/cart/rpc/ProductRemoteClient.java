package com.kkb.cubemall.cart.rpc;

import com.kkb.cubemall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ProductRemoteClient
 * @Description
 * @Author hubin
 * @Date 2021/5/26 17:20
 * @Version V1.0
 **/
@FeignClient("cubemall-product")
public interface ProductRemoteClient {

    @RequestMapping("product/skuinfo/info/{skuId}")
    public R getSkuInfo(@PathVariable("skuId") Long skuId);

    // 查询商品规格属性
    @RequestMapping("product/skusaleattrvalue/sale/{skuId}")
    public List<String> getSkuSaleAttrs(@PathVariable("skuId") Long skuId);

    // 查询商品最新的价格信息
    @RequestMapping("product/skuinfo/price/{skuId}")
    public BigDecimal getPrice(@PathVariable("skuId") Long skuId);
}
