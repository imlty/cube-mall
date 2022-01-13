package com.kkb.cubemall.search.service;

import com.kkb.cubemall.common.utils.R;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface SpuInfoService {

    R putOnSale(Long spuId);

    R syncSpuInfo();

    Map<String,Object> search(Map<String,String >paramMap);

}
