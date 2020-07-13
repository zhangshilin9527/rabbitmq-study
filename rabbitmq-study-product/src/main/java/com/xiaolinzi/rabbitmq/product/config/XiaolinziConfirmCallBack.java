package com.xiaolinzi.rabbitmq.product.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @author ：zhangshilin
 * @date ：2020-07-13 14:50
 */
public class XiaolinziConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    private static final Logger logger = LoggerFactory.getLogger(XiaolinziConfirmCallBack.class);
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("id:{}",correlationData.getId());
        if(ack) {
            logger.info("mq生产端消息已经成功投递到了broker");
        }else {
            logger.error("mq生产端没有投递成功,原因:{}",cause);
        }
    }
}
