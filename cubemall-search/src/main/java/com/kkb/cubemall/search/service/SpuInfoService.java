package com.kkb.cubemall.search.service;

import com.kkb.cubemall.common.utils.R;
import org.springframework.stereotype.Service;

public interface SpuInfoService {

    R putOnSale(Long spuId);

    R syncSpuInfo();

}
