package com.kkb.cubemall.search.repostory;

import com.kkb.cubemall.search.model.Blog;
import com.kkb.cubemall.search.model.SpuInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author: sublun
 * @Date: 2021/4/22 18:31
 */
public interface GoodsRepostory extends ElasticsearchRepository<SpuInfo, Long> {
}
