package com.kkb.cubemall.search.controller;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.search.entity.BrandEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: sublun
 * @Date: 2021/4/24 14:19
 */
@RestController
public class HelloWorldController {
    @GetMapping("/hello/{name}")
    public R sayHello(@PathVariable String name) {
        return R.ok("hello " + name);
    }

    @GetMapping("/hello")
    public R sayHello2(String name) {
        return R.ok("hello " + name);
    }

    @PostMapping("/brand")
    public R postBrand(@RequestBody BrandEntity brand) {
        return R.ok(brand);
    }

    @PostMapping("/brand2")
    public R postBrand(@RequestBody BrandEntity brand, int seq) {
        R r = R.ok();
        r.put("brand", brand);
        r.put("seq", seq);
        return r;
    }
}
