package com.kkb.cubemall.coupon.listeners;

import okhttp3.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
 * 目标：接收数据同步微服务消息刷新redis缓存
 * 实现步骤：
 *  1.搭建运营微服务
 *  2.配置依赖和编写启动引导类
 *  3.编写监听器：监听消息队列。执行更新刷新缓存逻辑
 *  http://192.168.200.128/ad_load?position=web_index_lb
 **/
@Component
//@RabbitListener(queues = "ad_update_queue")
public class UpdateAdCacheListener {
    /**
     *  监听触发的方法
     */
//    @RabbitHandler//监听方法
    public void updateAd(String message){
        System.out.println("接收到消息：" + message);

        //刷新缓存http://192.168.200.128/ad_load?position=web_index_lb
        String url = "http://192.168.200.128/ad_load?position=web_index_lb";
        OkHttpClient okHttpClient = new OkHttpClient();
        //构建请求对象
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        //客户端发送请求
        Call call = okHttpClient.newCall(request);
        //设置回调监听：监听响应对象的内容
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("调用成功了：" + response.message());
            }
        });

    }
}
