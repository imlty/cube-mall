package com.kkb.cubemall.mybatis;

import com.kkb.cubemall.search.CubemallSearchApplication;
import com.kkb.cubemall.search.dao.SpuInfoDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CubemallSearchApplication.class)
public class mybatisTest {

    @Autowired
    private SpuInfoDao spuInfoDao;

    @Test
    public void testSpuInfo() {
        System.out.println(spuInfoDao.selectById(7));
    }

    @Test
    public void testGetSpuInfoById() {
        System.out.println(spuInfoDao.getSpuInfoById(7L));
    }
}
