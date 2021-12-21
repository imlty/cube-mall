package com.kkb.cubemall.search.controller;

import com.kkb.cubemall.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public R sayHello(){
        return R.ok("hello!");
    }
}
