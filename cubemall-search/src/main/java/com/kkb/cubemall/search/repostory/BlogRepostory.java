package com.kkb.cubemall.search.repostory;

import com.kkb.cubemall.search.model.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author: sublun
 * @Date: 2021/4/22 18:31
 */
public interface BlogRepostory extends ElasticsearchRepository<Blog, Long> {
    List<Blog> findByTitle(String title);
    List<Blog> findByContent(String content, Pageable page);
}
