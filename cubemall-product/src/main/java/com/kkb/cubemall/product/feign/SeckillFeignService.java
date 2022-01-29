package com.kkb.cubemall.product.feign;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.feign.fallback.SeckillFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cubemall-seckill",fallback = SeckillFeignServiceFallback.class)
public interface SeckillFeignService {

    @GetMapping(value = "/sku/seckill/{skuId}")
    R getSeckillSkuRedisTo(@PathVariable("skuId") Long skuId);
}
