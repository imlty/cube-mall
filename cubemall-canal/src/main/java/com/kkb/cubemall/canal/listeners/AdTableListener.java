package com.kkb.cubemall.canal.listeners;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 * 目标：监听数据库cube-coupon中tb_ad表的变化
 * 步骤：
 *  1.搭建微服务：
 *      创建项目
 *      导入依赖坐标
 *      配置文件
 *  2.编写监听的代码
 *
 *  总体步骤：
 *
 **/
@CanalEventListener//声明当前为Canal事件监听器
public class AdTableListener {

    //导入RabbitMQ的模板对象
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @ListenPoint
     *
     * CanalEntry.EventType数据库监听响应事件：新增、修改、删除、查询..
     *
     * 向MQ发送消息：更新缓存的消息
     */
    @ListenPoint(schema = "cube_coupon",table = "tb_ad")
    public void adUpdateListenerHandle(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        System.out.println("广告数据发送变化");
        //事件发生之前数据：原始数据
        List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
        beforeColumnsList.forEach((column -> System.out.println("更改前的数据："+column.getName()+"::"+column.getValue())));
        //获取时间发送变化之后的数据
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        afterColumnsList.forEach((column -> System.out.println("更改后的数据："+column.getName()+"::"+column.getValue())));
        //column是数据库表中的列名：id、name、p
        for (CanalEntry.Column column : afterColumnsList) {
            if (column.getName().equals("position")) {
                System.out.println("发送消息到MQ：ad_update_queue -->::"+column.getValue());//web_index_lb
                rabbitTemplate.convertAndSend("","ad_update_queue",column.getValue());
            }
        }
    }
}
