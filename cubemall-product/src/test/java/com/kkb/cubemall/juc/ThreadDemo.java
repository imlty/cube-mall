package com.kkb.cubemall.juc;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;

import javax.swing.text.rtf.RTFEditorKit;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @ClassName ThreadDemo
 * @Description
 * @Author hubin
 * @Date 2021/5/10 19:58
 * @Version V1.0
 *
 *  1）、继承Thread类，实现多线程
 *      Thread01 extends Thread
 *       // 创建多线程对象
        Thread01 thread01 = new Thread01();
        // 开启线程
        thread.start();
    2）、实现Runnable接口，实现多线程
    （1）普通构建方式：
        // 创建多线程对象
        Thread02 thread02 = new Thread02();
        // 创建多线程对象
        Thread thread = new Thread(thread02);
        // 开启线程
        thread.start();
    （2）、匿名内部类的构建方式
        Runnable runnable = new Runnable() {
        @Override
        public void run() {
        log.info("继承Thread实现方式.......");
        // 业务代码执行
        int i = 100/3;
        log.info("业务代码执行结果：{}",i);
        }
        };

        // 创建线程对象
        Thread thread = new Thread(runnable);
        // 开启线程执行
        thread.start();
     （3）、lambda 表达式实现
        new Thread(()->{
        log.info("继承Thread实现方式.......");
        // 业务代码执行
        int i = 100/3;
        log.info("业务代码执行结果：{}",i);

        }).start();
        lambda 表达式特点：
        1、@FunctionalInterface： 此直接表示可以使用lambda表达式的编程方式，此注解相当于是一个标识
        2、接口只有一个方法（必须满足）-- 即使没有上面注解，也可以使用lambda表达式；程序员会在后台自动识别
        写作形式：
        方法括号(有参写，无参不写) -> {业务执行方法体}
    3）、Callable + FutureTask实现多线程
        jdk1.5后： 添加callable接口，实现多线程，相较于Thread, runnable接口没有返回值，但是callable接口是有返回值。
        @FunctionalInterface : 支持lambda表达式写法
        public interface Callable<V> ：
            1、具有泛型的接口，只有一个方法call，call方法就是多线程执行业务主体，
            2、方法执行完毕后有返回值，返回值类型就是制定的泛型类型
        疑问：多线程执行必须和Thread有关系，callable接口和thread有什么关系呢？？
        实现方式：
        1、普通实现方式
        Thread03 thread03 = new Thread03();  Thread03 implements Callable<Integer>
        // 创建futureTask对象，把thread03对象传递构造函数中
        FutureTask<Integer> task = new FutureTask<Integer>(callable);
        // 创建多线程对象
        Thread thread = new Thread(task);
        // 开启线程执行
        thread.start();
        Integer integer = task.get();

        2、匿名内部类
        Callable<Integer> callable = new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
        log.info("继承Thread实现方式.......");
        // 业务代码执行
        int i = 100/3;
        log.info("业务代码执行结果：{}",i);
        return  i;
        }
        };

        3、lambda表达式
        FutureTask<Integer> task = new FutureTask<Integer>(()->{
        log.info("继承Thread实现方式.......");
        // 业务代码执行
        int i = 100/3;
        log.info("业务代码执行结果：{}",i);
        return  i;
        });



 **/
@Slf4j
public class ThreadDemo {

    public static void main(String[] args) {

        System.out.println("thread.........start........");

        // 创建对象：实现了callable接口对象
        //Thread03 thread03 = new Thread03();

       /* Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("继承Thread实现方式.......");
                // 业务代码执行
                int i = 100/3;
                log.info("业务代码执行结果：{}",i);
                return  i;
            }
        };*/



        // 创建futureTask对象，把thread03对象传递构造函数中
        FutureTask<Integer> task = new FutureTask<Integer>(()->{
            log.info("继承Thread实现方式.......");
            // 业务代码执行
            int i = 100/3;
            log.info("业务代码执行结果：{}",i);
            return  i;
        });

        // 创建多线程对象
        Thread thread = new Thread(task);
        // 开启线程执行
        thread.start();


        // 等待子线程执行结束后，获取返回结果
        try {

            // 同步阻塞，必须等待异步线程执行结束，且返回结果后，再继续往下执行
            Integer integer = task.get();

            System.out.println("子线程执行结果："+integer);



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        System.out.println("thread.........end........");



    }


    // 01-多线程第一种实现方式： extends Thread
    public static class ThreadA extends Thread{

        // run是线程执行主体；多线程业务在run方法中运行
        @Override
        public void run() {
            log.info("继承Thread实现方式.......");
            // 业务代码执行
            int i = 100/3;
            log.info("业务代码执行结果：{}, 线程名称:{}, 线程ID：{}",i,this.getName(),this.getId());
        }
    }


    // 02-实现Runnable接口，实现多线程
    public static class Thread02 implements Runnable{

        @Override
        public void run() {
            log.info("继承Thread实现方式.......");
            // 业务代码执行
            int i = 100/3;
            log.info("业务代码执行结果：{}",i);
        }
    }



    // 03-实现callable接口，实现多线程
    public static class Thread03 implements Callable<Integer>{



        // 业务执行主体
        @Override
        public Integer call() throws Exception {
            log.info("继承Thread实现方式.......");
            // 业务代码执行
            int i = 100/3;
            log.info("业务代码执行结果：{}",i);
            return  i;
        }
    }



}

