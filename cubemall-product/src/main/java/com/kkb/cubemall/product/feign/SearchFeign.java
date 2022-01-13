package com.kkb.cubemall.product.feign;

import com.kkb.cubemall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cubemall-search")
public interface SearchFeign {


    @GetMapping("/hello")
    public R sayHello();

    @GetMapping("/spuInfo/putonsale/{spuId}")
    public R putOnSale(@PathVariable("spuId") Long spuId) throws Exception;
}
