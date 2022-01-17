package com.kkb.cubemall.juc;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.field.FieldDescription;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import sun.swing.plaf.synth.DefaultSynthStyle;

import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ClassName AsycFutureDemo
 * @Description 并发编程中异步编排
 * @Author hubin
 * @Date 2021/5/13 14:35
 * @Version V1.0
 **/
@Slf4j
public class AsycFutureDemo {

    public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            9,
            3,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());



    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //public <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn);
        // try{}finally{} : 对上一步执行结果进行处理，还可以处理异常任务


        log.info("主线程start............");
        // 第一个：CompletionStage
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 2;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        }).handle((t, u) -> {
            int res = -1;
            if (u != null) {
                log.info("执行错误：{}", u.getMessage());
            } else {
                res = t * 5;
            }

            return res;
        });

        Integer integer = f.get();
        log.info("最终执行结果:{}",integer);

        log.info("主线程end............");

    }

    public static void whenComplete(String[] args) throws ExecutionException, InterruptedException {

        //public CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action);


        log.info("主线程start............");
        // 第一个：CompletionStage
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 0;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        }).whenComplete((t, u) -> {

            log.info("上一步执行结果:{}",t);

            // 判断
            if(u!=null){
                log.info("执行错误，有异常:{}",u.getMessage());
            }
        });

        Integer integer = f.get();
        log.info("最终执行结果:{}",integer);

        log.info("主线程end............");

    }





    public static void exceptionally(String[] args) throws ExecutionException, InterruptedException {

        //public CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn)

        log.info("主线程start............");
        // 第一个：CompletionStage
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 0;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        }).exceptionally((t)->{
            log.info("业务执行失败:{}",t.getMessage());
            return null;
        });

        Integer integer = f.get();

        log.info("主线程end............");

    }


    public static void applyToEither(String[] args) throws ExecutionException, InterruptedException {

       // public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn);
       // applyToEither特点：
       // 1、针对于2个阶段CompletionStage，将计算速度最快的那个CompletionStage的结果作为下一步处理的消费；
       // 2、有返回值
        log.info("主线程start............");
        // 第一个：CompletionStage
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 4;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        });
        // 第二个：CompletionStage
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 3;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        });
        // 利用applyToEither方法对f1,f2进行合并操作，合并操作关系：OR
        CompletableFuture<Integer> f = f1.applyToEither(f2, result -> {
            log.info("applyToEither子线程开始执行，参数是：{}",result);
            return result;
        });
        Integer r1 = f.get();
        log.info("最终计算结果：{}", r1);

        log.info("主线程end............");

    }


    public static void runAfterBoth(String[] args) throws ExecutionException, InterruptedException {

        //1、public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
        // runAfterBoth: 无返回值，当2个阶段的CompletionStage都执行完毕后，才会执行下一步操作
        //public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,Runnable action)
        log.info("主线程start............");
        // 第一个：CompletionStage
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 4;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        });
        // 第二个：CompletionStage
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 3;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        });
        // 利用runAfterBoth方法对f1,f2进行合并操作
        CompletableFuture<Void> f = f1.runAfterBoth(f2, () -> {
            log.info("有个任务在执行：runAfterBoth方法正在运行.......");
        });

        f.get();


        log.info("主线程end............");

    }



    public static void thenAcceptBoth(String[] args) throws ExecutionException, InterruptedException {

        //1、public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
        //thenAcceptBoth: 当2个阶段的CompletionStage都执行完毕后，把结构一块交给thenAcceptBoth进行执行,没有返回值
        //public <U> CompletionStage<Void> thenAcceptBoth (CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);

        log.info("主线程start............");
        // 第一个：CompletionStage
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 4;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        });
        // 第二个：CompletionStage
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 3;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        });
        // 利用thenCombine方法对f1,f2进行合并操作
        CompletableFuture<Void> f = f1.thenAcceptBoth(f2, (t, u) -> {
            log.info("第一个 CompletableFuture 执行结果:{}", t);
            log.info("第二个 CompletableFuture 执行结果:{}", u);
        });
        // 调用异步方法
        f.get();



        log.info("主线程end............");
    }


    public static void thenCombine(String[] args) throws ExecutionException, InterruptedException {

        //1、public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
        //thenCombine:
        //1）、有返回值
        //2)、thenCombine会把两个CompletionStage的任务都执行完毕后，把两个任务的结果一块交给thenCombine去处理
        //2、public <U,V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
        log.info("主线程start............");

        // 第一个：CompletionStage
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 4;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        });

        // 第一个：CompletionStage
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 3;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        });

        // 利用thenCombine方法对f1,f2进行合并操作
        CompletableFuture<Integer> thenCombine = f1.thenCombine(f2, (t, u) -> {
            log.info("第一个 CompletableFuture 执行结果:{}", t);
            log.info("第二个 CompletableFuture 执行结果:{}", u);

            return t+u;

        });
        // 调用异步方法
        Integer integer = thenCombine.get();
        log.info("最终异步的调用结果：{}", integer);



        log.info("主线程end............");


    }



    public static void thenCompose(String[] args) throws ExecutionException, InterruptedException {

        //1、public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
        //thenCompose:
        //1）、有返回值，返回值类型U
        //2)、依赖于上一步的返回值结果，上一步返回值结果将会作为参数被传递
        //3)、允许对2个CompletionStage流水线进行操作，第一个操作完成时，将第一个操作结果传递第二个CompletionStage
        //2、public <U> CompletionStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn);

        CompletableFuture<Long> thenCompose = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 2;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        }).thenCompose(new Function<Integer, CompletionStage<Long>>() {
            @Override
            public CompletionStage<Long> apply(Integer t) {
                // 第二次执行
                CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
                    log.info("thenCompose子线程开始执行，参数是：{}", t);
                    long res = t * 5;
                    log.info("计算结果：{}", res);
                    return res;
                });

                return future;
            }
        });

        // 调用异步方法
        Long aLong = thenCompose.get();
        log.info("最终计算结果：{}", aLong);

    }


    public static void thenAccept(String[] args) throws ExecutionException, InterruptedException {

        //1、public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
        //thenAccept:没有返回值，跟上一步执行结果有关系，上一步执行结果将会被下一步消费，参数类型T
        //2、public CompletionStage<Void> thenAccept(Consumer<? super T> action);

        log.info("主线程start............");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 2;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        }).thenAccept((t) -> {
            log.info("thenAccept子线程开始执行，参数是：{}", t);
            long res = t * 5;
            log.info("计算结果：{}", res);
        });

        // 调用异步方法
        future.get();

        log.info("主线程end............");

    }




    public static void thenApply(String[] args) throws ExecutionException, InterruptedException {
        //1、public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
        // thenApply 有返回值，返回值类型是U, 跟上一步执行结果有关系，上一步执行结果会被当成参数传递给下一步，参数类型为T
        //2、public <U> CompletionStage<U> thenApply(Function<? super T,? extends U> fn);

        // 多线程异步编排
        CompletableFuture<Long> thenApply = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 2;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        }).thenApply((t) -> {
            log.info("thenApply子线程开始执行，参数是：{}", t);
            long res = t * 5;
            log.info("计算结果：{}", res);
            return res;
        });

        // 调用异步方法
        Long aLong = thenApply.get();

        log.info("最终计算结果：{}",aLong);

    }


    public static void thenRunAsync(String[] args) throws ExecutionException, InterruptedException {
       //1、public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
       // thenRun 没有返回值，也不关心上一步执行结果，只和上一步执行具有顺序关系
       //2、public CompletionStage<Void> thenRun(Runnable action);

        log.info("主线程start............");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("子线程future线程start............");
            int i = 10 / 2;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);
            log.info("子线程future线程end............");
            return i;
        }).thenRunAsync(() -> {
            log.info("thenRun子线程开始运行............");
        });

        // 调用执行
        future.get();
        log.info("主线程end............");

    }




    public static void supplyAsync(String[] args) throws ExecutionException, InterruptedException {

        log.info("主线程start............");

        //supplyAsync: 实现异步编排，有返回值
        //public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
        CompletableFuture<Integer> uCompletableFuture = CompletableFuture.supplyAsync(() -> {

            log.info("子线程future线程start............");
            int i = 10 / 2;
            log.info("线程名称：{},线程执行结果：{}", Thread.currentThread().getName(), i);

            log.info("子线程future线程end............");

            return i;
        });

        // 调用异步编排future
        Integer integer = uCompletableFuture.get();

        log.info("supplyAsync异步编排的返回值结果：{}",integer);

        log.info("主线程end............");





    }

    public static void runAsync(String[] args) throws ExecutionException, InterruptedException {

        log.info("主线程start............");

        //runAsync: 实现异步编排，没有返回值
        //public static CompletableFuture<Void> runAsync(Runnable runnable);
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

            log.info("子线程future线程start............");
            int i = 10/2;
            log.info("线程名称：{},线程执行结果：{}",Thread.currentThread().getName(),i);

            log.info("子线程future线程end............");

        },threadPool);

        // 调用异步任务
        future.get();

        log.info("主线程end............");

    }

}

