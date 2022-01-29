package com.kkb.cubemall.seckill.scheduled;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@EnableAsync
public class HelloScheuled {


    @Async
//    @Scheduled(cron = "* * * * * ?")
    public void  printLog() throws InterruptedException {

        log.info("hello how are you :" + LocalTime.now());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        CompletableFuture.runAsync(()->{
//            log.info("hello how are you :" + LocalTime.now());
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });


    }
}
