package com.kkb.cubemall.product.feign;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.entity.BrandEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: sublun
 * @Date: 2021/4/24 14:20
 */
@FeignClient("cubemall-search")
public interface SearchFeign {
    @GetMapping("/hello/{name}")
    R sayHello(@PathVariable("name") String name);

    @GetMapping("/hello")
    R sayHello2(@RequestParam("name") String name);

    @PostMapping("/brand")
    public R postBrand(@RequestBody BrandEntity brand);

    @PostMapping("/brand2")
    public R postBrand(@RequestBody BrandEntity brand, @RequestParam("seq") int seq);

    /**
     * 商品上架，将商品同步到ES中
     * @param spuId
     * @return
     */
    @GetMapping("/searchManage/putonsale/{spuId}")
    R putOnSale(@PathVariable("spuId") Long spuId);
}
