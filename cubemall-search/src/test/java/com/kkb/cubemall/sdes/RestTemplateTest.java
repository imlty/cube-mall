package com.kkb.cubemall.sdes;

import com.kkb.cubemall.search.CubemallSearchApplication;
import com.kkb.cubemall.search.model.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: sublun
 * @Date: 2021/4/25 18:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CubemallSearchApplication.class)
public class RestTemplateTest {
    @Autowired
    private ElasticsearchRestTemplate template;

    @Test
    public void createIndex() {
        template.indexOps(IndexCoordinates.of("mytest")).create();
    }

    @Test
    public void putMappint() {
        Document mapping = template.indexOps(IndexCoordinates.of("mytest")).createMapping(Blog.class);
        template.indexOps(IndexCoordinates.of("mytest")).putMapping(mapping);
    }

    @Test
    public void createIndexWithMapping() {
        //template.indexOps(Blog.class).create();
        Document mapping = template.indexOps(IndexCoordinates.of("mytest")).createMapping(Blog.class);
        template.indexOps(Blog.class).putMapping(mapping);
    }

    @Test
    public void deleteIndex() {
        template.indexOps(IndexCoordinates.of("hello1")).delete();
    }
}
