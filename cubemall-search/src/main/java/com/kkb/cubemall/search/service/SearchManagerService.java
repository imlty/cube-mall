package com.kkb.cubemall.search.service;

import com.kkb.cubemall.common.utils.R;

public interface SearchManagerService {


    /**
     * 导入全部spu到ES中
     */
    R importAll();

    R addSpuById(Long spuId);


}
