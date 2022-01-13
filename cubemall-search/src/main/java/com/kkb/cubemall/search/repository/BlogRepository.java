package com.kkb.cubemall.search.repository;

import com.kkb.cubemall.search.model.Blog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author: sublun
 * @Date: 2021/4/26 10:58
 */
public interface BlogRepository extends ElasticsearchRepository<Blog, Long> {
    List<Blog> findByTitle(String title);
    List<Blog> findByTitleAndContent(String title, String content);
}
