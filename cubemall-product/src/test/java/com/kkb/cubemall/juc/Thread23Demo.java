package com.kkb.cubemall.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * @ClassName Thread23Demo
 * @Description
 * @Author hubin
 * @Date 2021/5/23 20:08
 * @Version V1.0
 **/
@Slf4j
public class Thread23Demo {

    public static void test1(String[] args) {

     /*  new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();*/

       new Thread(()->{
           System.out.println("lambda表达式的方式创建多线程!");
       }).start();


    }


    public static void main(String[] args) {
        //Function
       /* Function<Integer,Integer> func = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return null;
            }
        }*/
        Function<Integer,Integer> func = p -> p*100;
        // 调用方法
        Integer res = func.apply(100);

        log.info("计算结果:{}",res);

    }


}

