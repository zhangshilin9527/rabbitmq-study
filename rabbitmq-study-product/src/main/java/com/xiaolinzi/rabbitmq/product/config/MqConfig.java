package com.xiaolinzi.rabbitmq.product.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private static final Logger log = LoggerFactory.getLogger(MqConfig.class);

    public static final String DIRECT_EXCHANGE = "xiaolinzi_direct";
    public static final String DIRECT_QUEUE = "xiaolinzi_direct_queue";
    public static final String DIRECT_QUEUE_KEY = "xiaolinzi_direct_queue_key";
    public static final String CONFIRM_RETURN_EXCHANGE = "xiaolinzi_confirm_return_exchange";
    public static final String CONFIRM_RETURN_QUEUE = "xiaolinzi_confirm_return_queue";
    public static final String CONFIRM_RETURN_KEY = "xiaolinzi_confirm_return_key";

    /**
     * 交换机
     * @return
     */
    @Bean
    public DirectExchange xiaolinziDirectExchange() {
        //durable 表示小时是否持久化
        //autoDelete 消息是否自动删除
        DirectExchange directExchange = new DirectExchange(DIRECT_EXCHANGE, true, false);
        return directExchange;
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue xiaolinziQueue() {
        //exclusive:是否排外的  一般等于true的话用于一个队列只能有一个消费者来消费的场景
        Queue queue = new Queue(DIRECT_QUEUE, true, false, false);
        return queue;
    }

    /**
     * 绑定关系
     * @return
     */
    @Bean
    public Binding xiaolinziBinder() {
        return BindingBuilder.bind(xiaolinziQueue()).to(xiaolinziDirectExchange()).with(DIRECT_QUEUE_KEY);
    }
    /**
     *消息一致性配置 start
     */


    @Bean("testConfirmDirectExchange")
    public DirectExchange confirmDirectExchange() {
       return (DirectExchange)ExchangeBuilder.directExchange(CONFIRM_RETURN_EXCHANGE).build();
//        return new DirectExchange(CONFIRM_RETURN_EXCHANGE, true, false);
    }

    @Bean("testConfirmQueue")
    public Queue deadQueue() {
        return QueueBuilder.durable(CONFIRM_RETURN_QUEUE).build();
    }

    // 声明死信队列绑定关系
    @Bean
    public Binding testConfirmBinding(@Qualifier("testConfirmQueue") Queue queue,
                               @Qualifier("testConfirmDirectExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_RETURN_KEY);
    }

    @Bean("asyncRabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //不指定序列化工具
        //rabbitTemplate.setMessageConverter( new Jackson2JsonMessageConverter());
        //发送成功确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功:correlationData({}),cause({})", correlationData.getId(), cause);
                log.info("消息发送成功:correlationData({}),cause({})", correlationData.getId(), cause);
            } else {
                log.error("消息未成功投递, id:{}, cause:{}", correlationData.getId(), cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息内容:{}",new String(message.getBody()));
                log.info("回复文本:{},回复代码：{}",replyText,replyCode);
                log.info("交换器名称:{},路由键：{}",exchange,routingKey);
            }
        });
        return rabbitTemplate;
    }

    /**
     *消息一致性配置 end
     */













}
