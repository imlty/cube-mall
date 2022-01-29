package com.kkb.cubemall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.search.dao.SpuInfoDao;
import com.kkb.cubemall.search.model.SpuInfo;
import com.kkb.cubemall.search.repostory.GoodsRepostory;
import com.kkb.cubemall.search.service.SearchManagerService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchManagerServiceImpl implements SearchManagerService {

    @Autowired
    private ElasticsearchRestTemplate template;
    @Autowired
    private GoodsRepostory goodsRepostory;
    @Autowired
    private SpuInfoDao spuInfoDao;




    @Override
    public R importAll() {
        //1.查询全部的sku列表数据
        List<SpuInfo> spuList = spuInfoDao.getSpuInfoList();
        List<IndexQuery> queryList = spuList.stream()
                .map(spu -> {
                    IndexQuery query = new IndexQuery();
                    query.setId(spu.getId().toString());
                    query.setObject(spu);
                    return query;
                })
                .collect(Collectors.toList());
        template.bulkIndex(queryList, IndexCoordinates.of("cube_goods"));
        return R.ok();

    }

    @Override
    public R addSpuById(Long spuId) {
        SpuInfo spuInfo = spuInfoDao.getSpuById(spuId);
        SpuInfo info = goodsRepostory.save(spuInfo);
        return R.ok(info);
    }


}
