package com.kkb.cubemall.seckill.service;


import com.kkb.cubemall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

public interface SeckillService {
    void uploadSeckillSkuLateset3Days();

    List<SeckillSkuRedisTo> getCurrentSeckillSuks();

    SeckillSkuRedisTo getSeckillSkuRedisTo(Long skuId);


    String kill(String killId,String key,Integer num);
}
