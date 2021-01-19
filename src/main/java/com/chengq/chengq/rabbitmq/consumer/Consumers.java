package com.chengq.chengq.rabbitmq.consumer;

import com.chengq.chengq.conf.MQConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Consumers {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = {MQConstants.CHENGQ_DEAL_QUEUE})
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) {

        String routingKey = (String) headers.get(AmqpHeaders.RECEIVED_ROUTING_KEY);
        String exchange = (String) headers.get(AmqpHeaders.RECEIVED_EXCHANGE);
        String body = new String(message.getBody());
        logger.info(String.format("消费结果========routingKey:%s====exchange:%s=====body:%s====", routingKey, exchange,
                body));
    }
}