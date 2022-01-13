package com.kkb.cubemall.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.kkb.cubemall.product.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CubemallProductApplicationTests {

    @Autowired
    private SearchFeign searchFeign;

    @Test
    public void testFegin(){
        System.out.println(searchFeign.sayHello());
    }

}
