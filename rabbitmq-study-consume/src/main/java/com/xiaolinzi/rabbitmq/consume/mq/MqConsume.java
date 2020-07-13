package com.xiaolinzi.rabbitmq.consume.mq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zhangshilin
 * @date ：2020-07-13 16:55
 */
@Component
public class MqConsume {
    private static final Logger logger = LoggerFactory.getLogger(MqConsume.class);

    @RabbitListener(queues = {"xiaolinzi_direct_queue"})
    @RabbitHandler
    public void consumerMsg(Message message, Channel channel) throws IOException {

        logger.info("消费消息:{}", message);
        //手工签收
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        logger.info("接受deliveryTag:{}", deliveryTag);
        channel.basicAck(deliveryTag, false);
    }
}
