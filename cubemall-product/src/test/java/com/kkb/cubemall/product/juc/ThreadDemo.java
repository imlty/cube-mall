package com.kkb.cubemall.product.juc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ThreadDemo {
    public static void main(String[] args) {
        log.info("main  {} -- {} -- start",Thread.currentThread().getName(),Thread.currentThread().getId());
//        ThreadA threadA1 = new ThreadA();
//        threadA1.start();

        ThreadB threadB = new ThreadB();
        Thread threadB1 = new Thread(threadB);
        threadB1.start();
        log.info("main end {}--{}",Thread.currentThread().getName(),Thread.currentThread().getId());
        // 运行结果 ：
        //start!
        //end!
        //14:09:29.900 [Thread-0] INFO com.kkb.cubemall.product.juc.ThreadDemo - 继承Thread的实现方式
        //14:09:29.904 [Thread-0] INFO com.kkb.cubemall.product.juc.ThreadDemo - 业务代码执行结果：33 -- 线程名称Thread-0 -- 线程14
        // 运行结果：著养成
        Runnable runnable = () -> {

            int a = 4;
        };
    }
    //实现方式：
    //1、普通实现方式
    public static class ThreadA extends Thread{

        // 重写方法
        @Override
        public void run() {
            log.info("继承Thread的实现方式 -- start");
            // 业务代码
            int a = 100/3;
            log.info("业务代码执行结果：{} -- 线程名称{} -- 线程{} -- end",a,this.getName(),this.getId());
        }
    }
    //实现方式：
    //2、实现runable接口，实现多线程
    public static class ThreadB implements Runnable{

        @Override
        public void run() {
            log.info("继承Thread的实现方式");
            // 业务代码
            int a = 100/3;
            log.info("业务代码执行结果：{} -- 线程名称{} -- 线程{}",a,Thread.currentThread().getName(),Thread.currentThread().getId());
        }
    }
    //实现方式：
    //1、普通实现方式
    //Thread03 thread03 = new Thread03(); Thread03 implements
    //Callable<Integer>
    //// 创建 futureTask 对象，把 thread03 对象传递构造函数中
    //FutureTask<Integer> task = new FutureTask<Integer>(callable);
    //// 创建多线程对象
    //Thread thread = new Thread(task);
    //// 开启线程执行
    //thread.start();
    //Integer integer = task.get();
    //2、匿名内部类
    //Callable<Integer> callable = new Callable<Integer>() {
    //@Override
    //public Integer call() throws Exception {
    //log.info("继承 Thread 实现方式.......");
    //// 业务代码执行
    //int i = 100/3;
    //log.info("业务代码执行结果：{}",i);
    //return i;
    //}
    //};
    //3、lambda 表达式
    //FutureTask<Integer> task = new FutureTask<Integer>(()->{
    //log.info("继承 Thread 实现方式.......");
    //// 业务代码执行
    //int i = 100/3;
    //log.info("业务代码执行结果：{}",i);
    //return i;
    //});

}
