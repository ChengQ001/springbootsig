package com.chengq.chengq.conf;

public class MQConstants {
    //死信队列 交换机标识符
    public static final String DEAD_LETTER_EXCHANGE_KEY = "x-dead-letter-exchange";

    //交换机名称
    public final static String CHENGQ_EXCHANGE = "chengq.exchange";
    //死信交换机名称
    public final static String CHNEGQ_DEAL_EXCHANGE = "chengq.deal.exchange";

    //死信消息队列名称
    public final static String CHENGQ_DEAL_QUEUE = "chengq.deal.queue";


    //路由键
    public final static String CHENGQ_ROUTING_KEY = "chengq.routing.key";

    //死信路由键
    public final static String CHENGQ_DEAD_ROUTING_KEY = "chengq.dead.routing.key";

    //死信队列交换机绑定键标识符
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    //常规队列名称
    public final static String CHENGQ_QUEUE = "chengq.queue";
}
