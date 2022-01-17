package com.kkb.cubemall.product;


import com.kkb.cubemall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CubemallProductApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testCategoryPath(){
        Integer[] categorys = categoryService.findCategoryPath(292);
        log.info("testCategoryPath");
        log.info(Arrays.toString(categorys));
    }

    @Test
    public void contextLoads() {
    }

}
