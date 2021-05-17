package com.chengq.chengq.rabbitmq.producer;

import cn.hutool.core.date.DateUtil;
import com.chengq.chengq.conf.MQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;

@Component
public class Producers {
    /**
     *https://blog.csdn.net/qq_40625058/article/details/105584732  安装插件https://www.cnblogs.com/isunsine/p/11572457.html
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean sendDelay(String body, Integer second) {
        try {
            rabbitTemplate.convertAndSend(MQConstants.DELAYED_EXCHANGE_NAME, MQConstants.DELAYED_ROUTING_KEY, body,
                    x -> {
                        x.getMessageProperties().setDelay(second * 1000); //毫秒
                        return x;
                    });
            logger.info(MessageFormat.format("=======队列生成结果:true========队列生产Body:{0} second:{1} ,执行method为:{2}",
                    body, second, "Producers.sendDelay"));
            return true;
        } catch (AmqpException e) {
            return false;
        }
    }


    public Boolean sendDelay(String body, Date pushTime) {
        Date now = new Date();
        Integer second ;
        if (now.compareTo(pushTime) > -1) {
            second = 0;
        } else {
            Long ms = DateUtil.betweenMs(new Date(), pushTime) / new Long("1000");
            second = ms.intValue();
        }
        return sendDelay(body, second);
    }
}
