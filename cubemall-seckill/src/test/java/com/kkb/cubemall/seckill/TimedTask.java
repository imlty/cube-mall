package com.kkb.cubemall.seckill;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class TimedTask {
    public static void main(String[] args) {
//        userThreadImpleTimeTask();
//        userTimeImpleTimedTask();
//        userScheduledExecutorServiceTimeTask();
        useScheduleThreadPoolExcutorImplTimeTask();
    }


    private static void useScheduleThreadPoolExcutorImplTimeTask(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Locat Time is" + new Date().toString());
            }
        };
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new
                ScheduledThreadPoolExecutor(1,new BasicThreadFactory.Builder().namingPattern("scheudle-poll-%d").daemon(false).build());
        scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable,0L,1L, TimeUnit.SECONDS);
    }

    private static void userScheduledExecutorServiceTimeTask(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Locat Time is" + new Date().toString());
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        //第一个: 任务 ，第二个:首次执行的延时时间，第三个: 定时任务的间隔时间，第四个 时间的单位
        service.scheduleAtFixedRate(runnable,0L,1L, TimeUnit.SECONDS);
    }


    private static void userTimeImpleTimedTask(){
        //第一参数 任务,第二个参数 首次执行执行的延迟时间，第三个参数: 定时任务执行的时间间隔,时间单位是毫秒
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Locat Time is" + new Date().toString());
            }
        },0L,1000L);
    }

    private static  void userThreadImpleTimeTask(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("Local Time："+ new Date().toString());

                    try {
                        //时间间隔，单位是毫秒
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
