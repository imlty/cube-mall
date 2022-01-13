package com.kkb.cubemall.product.juc;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;

@Log4j2
public class 异步编排 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("main started");

        // 自定义线程池
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

//        // 1. runAsync : 实现异步编排，没有返回值
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            log.info("线程开始执行........");
//            int i1 = 100 / 3;
//            log.info("结果 {}", i1);
//        }, threadPool);
//        //  调用
//        future.get();
//        log.info("线程执行结束........");
//        // 2. supplyAsync : 实现异步编排，有返回值 [会阻塞]
//        CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> {
//            log.info("线程开始执行........");
//            int i1 = 100 / 3;
//            log.info("结果 {}", i1);
//            return i1;
//        }, threadPool);
//        System.out.println(future2.get());

//        // 3. thenRun 没有返回值，也不关心上一步执行结果，之和上一步执行具有顺序关系
//        CompletableFuture<Void> thenRun = CompletableFuture.supplyAsync(() -> {
//            log.info("线程开始执行........");
//            int i1 = 100 / 3;
//            log.info("结果 {}", i1);
//            return i1;
//        }, threadPool).thenRun(() -> {
//            log.info("thenRun线程开始执行........");
//            int i1 = 100 / 3;
//            log.info("结果 {}", i1);
//        });
//        // 4. thenRunAsync 没有返回值，也不关心上一步执行结果，之和上一步执行具有顺序关系
//        CompletableFuture<Void> thenRun = CompletableFuture.supplyAsync(() -> {
//            log.info("线程开始执行........");
//            int i1 = 100 / 3;
//            log.info("结果 {}", i1);
//            return i1;
//        }, threadPool).thenRunAsync(() -> {
//            log.info("thenRun线程开始执行........");
//            int i1 = 100 / 3;
//            log.info("结果 {}", i1);
//        });
//        // 5. thenApply 此方法有返回值，上一步直接的结果当成传参的传递给 thenApply
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//                    log.info("线程开始执行........");
//                    int i1 = 100 / 3;
//                    log.info("结果 {}", i1);
//                    return i1;
//                }, threadPool)
//                .thenApply((t) -> {
//                    log.info("thenApply线程开始执行........{}", t);
//                    int i1 = 100 / 3;
//                    log.info("结果 {}", i1);
//                    return i1;
//                });
//        // 6. thenCompose 此方法有返回值，上一步直接的结果当成传参的传递给 thenCompose,后续可以继续创建新线程并使用上个线程传递过来的值
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//                    log.info("线程开始执行第一次........");
//                    int i1 = 100 / 3;
//                    log.info("结果 {}", i1);
//                    return i1;
//                }, threadPool)
//                .thenCompose(new Function<Integer, CompletionStage<Integer>>() {
//                    @Override
//                    public CompletionStage<Integer> apply(Integer integer) {
//                        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
//                            log.info("线程thenCompose开始执行第二次........{}",integer);
//                            int i1 = 100 / 3;
//                            log.info("结果 {}", i1);
//                            return i1;
//                        },threadPool);
//                        return completableFuture;
//                     }
//                });
        // 7. thenCombine 此方法有返回值，把两个 completionStage得任务都执行完毕后，把两个任务的结果交给thenCombine去处理

//        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
//            log.info("线程开始执行第一次........");
//            int i1 = 100 / 4;
//            log.info("结果 {}", i1);
//            return i1;
//        }, threadPool);
//        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
//            log.info("线程开始执行第二次........");
//            int i1 = 100 / 3;
//            log.info("结果 {}", i1);
//            return i1;
//        }, threadPool);
//        CompletableFuture<Integer> future = f1.thenCombine(f2, (t, u) -> {
//            log.info("{}  {}", t, u);
//            return t + u;
//        });
//        // 7. thenAfterBoth 此方法没有返回值，把两个 completionStage得任务都执行完毕后，才会执行下一步操作
//
//        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
//            log.info("线程开始执行第一次........");
//            int i1 = 100 / 4;
//            log.info("结果 {}", i1);
//            return i1;
//        }, threadPool);
//        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
//            log.info("线程开始执行第二次........");
//            int i1 = 100 / 3;
//            log.info("结果 {}", i1);
//            return i1;
//        }, threadPool);
//        CompletableFuture<Void> future1 = f1.runAfterBoth(f2, () -> log.info("runAfterBoth任务开始执行。。。"));
//        CompletableFuture<Void> future2 = f1.runAfterEither(f2, () -> log.info("runAfterEither任务开始执行。。。"));
        // 8. exceptionally 异常处理  ；whenComplete 可获取上个处理结果和异常； handle 理解为 finally

//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            log.info("线程开始执行第一次........");
//            int a = 4/ 0;
//            int i1 = 100 / 4;
//            log.info("结果 {}", i1);
//            return i1;
//        }, threadPool).exceptionally((e) -> {
//            log.info("业务执行失败{}", e.getMessage());
//            return null;
//        });
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("线程开始执行第一次........");
            int a = 4 / 0;
            int i1 = 100 / 4;
            log.info("结果 {}", i1);
            return i1;
        }, threadPool).whenComplete((t, e) -> {
            log.info("上一步执行结果{}", t);
            log.info("业务执行失败{}", e.getMessage());
        });


        log.info("main ended");

    }

}
