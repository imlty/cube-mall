package com.kkb.cubemall.seckill.scheduled;


import com.kkb.cubemall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SeckillScheduled {


    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedissonClient redissonClient;


    private final String upload_lock = "seckill:upload:lock";

//    @Scheduled(cron = "0 * * * * ?")
    public void uploadSeckillLatest3Days(){

        log.info("上架秒杀的商品");
        //为避免并发操作，引入分布式锁
        RLock lock = redissonClient.getLock(upload_lock);
        try{
            //加锁
            lock.lock(10, TimeUnit.SECONDS);
            seckillService.uploadSeckillSkuLateset3Days();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放锁
            lock.unlock();
        }

    }
}
