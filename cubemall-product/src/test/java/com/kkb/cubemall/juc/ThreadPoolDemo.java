package com.kkb.cubemall.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolDemo
 * @Description
 * @Author hubin
 * @Date 2021/5/11 15:00
 * @Version V1.0
 **/
@Slf4j
public class ThreadPoolDemo {


    public static void main(String[] args) {

        // 1、创建线程池对象;创建单个线程的线程池对象
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        // 2、创建固定数量的线程池（指定核心线程数数量）,核心线程数为2
        ExecutorService executorService2 = Executors.newFixedThreadPool(2);

        //3、创建一个按照计划执行的线程池
        ScheduledExecutorService executorService3 = Executors.newScheduledThreadPool(2);
        
        //4、创建一个自动增长的线程池
        ExecutorService executorService4 = Executors.newCachedThreadPool();


        // 可伸缩
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                9,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        // 线程执行
        try {
            for (int i = 0; i < 10 ; i++) {

                threadPool.execute(()->{
                    log.info("Executors创建线程池的方式实现多线程.......");
                    // 业务代码执行
                    int j = 100/3;
                    log.info("业务代码执行结果：{}",j);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //线程池用完，关闭线程池
            threadPool.shutdown();


        }


    }




}

