package com.kkb.cubemall.sdes;

import com.kkb.cubemall.search.CubemallSearchApplication;
import com.kkb.cubemall.search.model.Blog;
import com.kkb.cubemall.search.repository.BlogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @Author: sublun
 * @Date: 2021/4/26 10:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CubemallSearchApplication.class)
public class BlogRepositoryTest {
    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void addDocument() {
        Blog blog = new Blog();
        for (int i = 0; i < 50; i++) {
            blog.setId((long) (i+1));
            blog.setTitle("测试文档" + (i+1));
            blog.setContent("测试文档的内容1");
            blog.setComment("注释内容");
            blog.setMobile("133444556677");
            blogRepository.save(blog);
        }
    }

    @Test
    public void updateDocument() {
        Optional<Blog> optional = blogRepository.findById(1l);
        if (optional.isPresent()) {
            Blog blog = optional.get();
            blog.setTitle("hello world");
            blogRepository.save(blog);
        }
    }

    @Test
    public void deleteDocument() {
        blogRepository.deleteById(1l);
        //blogRepository.deleteAll();
    }

    @Test
    public void getById() {
        /*Optional<Blog> optional = blogRepository.findById(1l);
        Blog blog = optional.get();
        System.out.println(blog);*/
        //Iterable<Blog> all = blogRepository.findAll();
        Iterable<Blog> all = blogRepository.findAll(PageRequest.of(1, 10));
        all.forEach(b-> System.out.println(b));

    }

    @Test
    public void testFindByTitle() {
        List<Blog> list = blogRepository.findByTitle("测试");
        list.stream().forEach(System.out::println);
    }

    @Test
    public void testFindByTitleAndContnet() {
        List<Blog> list = blogRepository.findByTitleAndContent("37", "内容");
        list.forEach(e-> System.out.println(e));
    }



}
