package com.kkb.cubemall.search.controller;

import com.kkb.cubemall.search.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 *
 */
@Controller
@RequestMapping("/test")
public class TestthymeleafController {

    @RequestMapping("/demo1")
    public String demo1(String id, Model model) {
        System.out.println("====id===" + id);

        //构造list数据给页面
        List<User> users = new ArrayList<User>();
        users.add(new User(1,"张三","深圳"));
        users.add(new User(2,"李四","北京"));
        users.add(new User(3,"王五","武汉"));


        //Map定义
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("01","饕鬄");
        dataMap.put("02","穷奇");
        dataMap.put("03","混沌");
        dataMap.put("04","梼杌");

        //存储一个数组
        String[] names = {"张三","李四","王五"};

        //日期
        model.addAttribute("now",new Date());

        //if条件
        model.addAttribute("age",22);

        //返回数据给页面
        model.addAttribute("names",names);
        model.addAttribute("dataMap",dataMap);
        model.addAttribute("users",users);
        model.addAttribute("id", id);
        model.addAttribute("hello", "欢迎来到thyemeleaf!");
        return "demo1";
    }
}
