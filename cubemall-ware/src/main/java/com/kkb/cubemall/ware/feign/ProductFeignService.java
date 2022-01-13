package com.kkb.cubemall.ware.feign;

import com.kkb.cubemall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("cubemall-product")
public interface ProductFeignService {

    /**
     * 信息
     */
    @RequestMapping("/product/skuinfo/info/{id}")
    public R info(@PathVariable("id") Long id);
}
