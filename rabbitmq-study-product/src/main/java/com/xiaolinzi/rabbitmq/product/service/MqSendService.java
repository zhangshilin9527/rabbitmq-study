package com.xiaolinzi.rabbitmq.product.service;

import com.alibaba.fastjson.JSONObject;
import com.xiaolinzi.rabbitmq.product.config.XiaolinziConfirmCallBack;
import com.xiaolinzi.rabbitmq.product.config.XiaolinziRetrunCallBack;
import com.xiaolinzi.rabbitmq.product.model.SendInfo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author ：xiaolinzi
 * @date ：2020-07-13 14:32
 * @email: xiaolinzi95_27.com
 */
@Service
public class MqSendService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        SendInfo sendInfo = bulidSendInfo();


        //构建correlationData ID:必须为全局唯一的
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //开启确认模式
        rabbitTemplate.setConfirmCallback(new XiaolinziConfirmCallBack());

        //开启消息可达监听
        rabbitTemplate.setReturnCallback(new XiaolinziRetrunCallBack());

        rabbitTemplate.convertAndSend("xiaolinzi_direct", "xiaolinzi_direct_queue_key", JSONObject.toJSONString(sendInfo), correlationData);


    }

    private SendInfo bulidSendInfo() {
        SendInfo sendInfo = new SendInfo();
        sendInfo.setName("小林子");
        sendInfo.setAge("8");
        return sendInfo;
    }
}
