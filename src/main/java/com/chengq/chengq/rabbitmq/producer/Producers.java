package com.chengq.chengq.rabbitmq.producer;

import com.chengq.chengq.conf.MQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class Producers {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * @method 生产者发送消息, direct模式下需要传递一个routingKey
     * @author ChengQ
     * @time 2020.08.12
     */
    public Boolean send(String body, Long second) {
        try {
            rabbitTemplate.convertAndSend(MQConstants.CHENGQ_EXCHANGE, MQConstants.CHENGQ_ROUTING_KEY
                    , body,
                    message -> {
                        // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue
                        // 的时候就指定好延迟时间还是在发送自己控制时间
                        message.getMessageProperties().setExpiration((second==null || second <= 0) ? 0 +
                                "" :
                                1000 * second + "");
                        return message;
                    });
            logger.info(MessageFormat.format("=======队列生成结果:true========队列生产Body:{0} second:{1} ,执行method为:{2}",
                    body, second, "Producers.send"));
            return true;
        } catch (Exception ex) {
            logger.info(MessageFormat.format("=======队列生成结果:false========队列生产Body:{0} second:{1} ==异常:{2}," +
                    "执行method为:{3}", body, second, ex.getMessage(), "Producers.send"));
            return false;
        }
    }

    public Boolean send(String body) {
        return this.send(body, 0L);
    }
}
