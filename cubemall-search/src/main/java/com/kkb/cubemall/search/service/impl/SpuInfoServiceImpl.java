package com.kkb.cubemall.search.service.impl;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.search.dao.SpuInfoDao;
import com.kkb.cubemall.search.model.SpuInfo;
import com.kkb.cubemall.search.repository.SpuInfoRepository;
import com.kkb.cubemall.search.service.SpuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("spuInfoService")
public class SpuInfoServiceImpl implements SpuInfoService {

    @Autowired
    private SpuInfoDao spuInfoDao;

    @Autowired
    private SpuInfoRepository spuInfoRepository;

    @Override
    public R putOnSale(Long spuId) {
        SpuInfo spuInfo = spuInfoDao.getSpuInfoById(spuId);
        spuInfoRepository.save(spuInfo);
        return R.ok();
    }

    @Override
    public R syncSpuInfo() {
        List<SpuInfo> spuInfos = spuInfoDao.getSpuInfos();
        spuInfoRepository.saveAll(spuInfos);
        return R.ok();
    }

    @Override
    public Map<String, Object> search(Map<String, String> paramMap) {
        return null;
    }
}
