package com.kkb.cubemall.seckill.feign;

import com.kkb.cubemall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("cubemall-product")
public interface ProductFeignService {

    @RequestMapping("/product/skuinfo/info/{id}")
    R getSkuInfo(@PathVariable("id") Long id);
}
