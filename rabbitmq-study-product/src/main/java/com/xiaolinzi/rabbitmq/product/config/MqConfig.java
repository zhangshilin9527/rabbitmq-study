package com.xiaolinzi.rabbitmq.product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：xiaolinzi
 * @date ：2020-07-13 14:05
 * @email: xiaolinzi95_27.com
 */
@Configuration
@EnableRabbit
public class MqConfig {


    public static final String DIRECT_EXCHANGE = "xiaolinzi_direct";
    public static final String DIRECT_QUEUE = "xiaolinzi_direct_queue";
    public static final String DIRECT_QUEUE_KEY = "xiaolinzi_direct_queue_key";

    @Bean
    public DirectExchange xiaolinziDirectExchange() {
        //durable 表示小时是否持久化
        //autoDelete 消息是否自动删除
        DirectExchange directExchange = new DirectExchange(DIRECT_EXCHANGE, true, false);
        return directExchange;
    }

    @Bean
    public Queue xiaolinziQueue() {
        //exclusive:是否排外的  一般等于true的话用于一个队列只能有一个消费者来消费的场景
        Queue queue = new Queue(DIRECT_QUEUE, true, false, false);
        return queue;
    }

    @Bean
    public Binding xiaolinziBinder() {
        return BindingBuilder.bind(xiaolinziQueue()).to(xiaolinziDirectExchange()).with(DIRECT_QUEUE_KEY);
    }
}
