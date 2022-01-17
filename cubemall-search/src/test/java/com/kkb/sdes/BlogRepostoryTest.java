package com.kkb.sdes;

import com.kkb.cubemall.search.CubemallSearchApplication;
import com.kkb.cubemall.search.model.Blog;
import com.kkb.cubemall.search.repostory.BlogRepostory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @Author: sublun
 * @Date: 2021/4/22 18:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CubemallSearchApplication.class)
public class BlogRepostoryTest {
    @Autowired
    private BlogRepostory repostory;

    @Test
    public void testFindByTitle() {
        List<Blog> blogs = repostory.findByTitle("电影");
        blogs.forEach(System.out::println);
    }

    @Test
    public void testFindByPage() {
        List<Blog> list = repostory.findByContent("电影", PageRequest.of(0, 5));
        list.forEach(e-> System.out.println(e));
    }

}
