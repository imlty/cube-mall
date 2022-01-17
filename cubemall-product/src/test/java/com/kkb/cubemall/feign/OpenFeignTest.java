package com.kkb.cubemall.feign;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.CubemallProductApplication;
import com.kkb.cubemall.product.entity.BrandEntity;
import com.kkb.cubemall.product.feign.SearchFeign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: sublun
 * @Date: 2021/4/24 14:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CubemallProductApplication.class)
public class OpenFeignTest {
   /* @Autowired
    private SearchFeign searchFeign;

    @Test
    public void testHelloWorld() {
        R r = searchFeign.sayHello("tom");
        System.out.println(r);
    }

    @Test
    public void testHelloWorld2() {
        R r = searchFeign.sayHello2("tom");
        System.out.println(r);
    }

    @Test
    public void testBrand() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1);
        brandEntity.setName("小米");
        brandEntity.setLetter("hello");
        R r = searchFeign.postBrand(brandEntity);
        System.out.println(r);
    }

    @Test
    public void testBrand2() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1);
        brandEntity.setName("小米");
        brandEntity.setLetter("hello");
        R r = searchFeign.postBrand(brandEntity, 1999);
        System.out.println(r);
    }*/

   @Test
    public void test(){

   }
}
