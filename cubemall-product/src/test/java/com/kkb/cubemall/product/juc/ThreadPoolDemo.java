package com.kkb.cubemall.product.juc;


import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;

@Log4j2
public class ThreadPoolDemo {

    public static void main(String[] args) {
        // 1. 创建线程池对象，创建单个线程的线程池对象
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        // 2. 创建固定数量的线程池（指定核心线程数数量），核心线程数为 2
        ExecutorService executorService2 = Executors.newFixedThreadPool(2);

        // 3. 创建一个按照计划执行的线程池
        ScheduledExecutorService executorService3 = Executors.newScheduledThreadPool(2);

        // 4. 创建一个自动增长的线程池
        ExecutorService executorService4 = Executors.newCachedThreadPool();

        // 现目前使用的线程池创建方式
        // 可伸缩的创建方式
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(), // 系统CPU核数
                9,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    log.info("线程开始执行........");
                    int i1 = 100 / 3;
                    log.info("线程执行结束........，结果 {}", i1);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }


    }
}
