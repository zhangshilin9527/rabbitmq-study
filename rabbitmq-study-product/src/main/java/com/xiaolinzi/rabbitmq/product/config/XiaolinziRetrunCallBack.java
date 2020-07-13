package com.xiaolinzi.rabbitmq.product.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author ：zhangshilin
 * @date ：2020-07-13 14:54
 */
public class XiaolinziRetrunCallBack implements RabbitTemplate.ReturnCallback {
    private static final Logger logger = LoggerFactory.getLogger(XiaolinziRetrunCallBack.class);

    /**
     * mandatory 设置为true才会回调到此方法，否则broker会自动删除投递失败的消息
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("消息投递失败 message{}", message);
        logger.info("消息投递失败 replyCode{}", replyCode);
        logger.info("消息投递失败 replyText{}", replyText);
        logger.info("消息投递失败 exchange{}", exchange);
        logger.info("消息投递失败 routingKey{}", routingKey);
        //TODO 消息投递失败的处理机制

    }
}
