package com.chengq.chengq.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {



    @Bean
    public Queue createQueue() {
        // 将普通队列绑定到死信队列交换机上
        Map<String, Object> args = new HashMap<>(2);
        //args.put("x-message-ttl", 5 * 1000);//直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活
        //这里采用发送消息动态设置延迟时间,这样我们可以灵活控制
        args.put(MQConstants.DEAD_LETTER_EXCHANGE_KEY, MQConstants.CHNEGQ_DEAL_EXCHANGE);
        args.put(MQConstants.DEAD_LETTER_ROUTING_KEY, MQConstants.CHENGQ_DEAD_ROUTING_KEY);
        return new Queue(MQConstants.CHENGQ_QUEUE, true, false, false, args);
    }

    //声明一个direct类型的交换机
    @Bean
    DirectExchange createExchange() {
        return new DirectExchange(MQConstants.CHENGQ_EXCHANGE);
    }

    //绑定Queue队列到交换机,并且指定routingKey
    @Bean
    Binding bindingExchange() {
        return BindingBuilder.bind(createQueue()).to(createExchange()).with(MQConstants.CHENGQ_ROUTING_KEY);
    }

    //创建配置死信队列
    @Bean
    public Queue createDeadQueue() {
        Queue queue = new Queue(MQConstants.CHENGQ_DEAL_QUEUE, true);
        return queue;
    }

    //创建死信交换机
    @Bean
    public DirectExchange createDeadExchange() {
        return new DirectExchange(MQConstants.CHNEGQ_DEAL_EXCHANGE);
    }

    //死信队列与死信交换机绑定
    @Bean
    public Binding bindingDeadExchange() {
        return BindingBuilder.bind(createDeadQueue()).to(createDeadExchange()).with(MQConstants.CHENGQ_DEAD_ROUTING_KEY);
    }
}
