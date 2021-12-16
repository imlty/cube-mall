package com.kkb.cubemall.search.repository;

import com.kkb.cubemall.search.model.SpuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SpuInfoRepository extends ElasticsearchRepository<SpuInfo, Long> {
}
