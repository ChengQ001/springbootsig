package com.chengq.chengq.provider;

import com.chengq.chengq.controller.HomeController;
import com.chengq.chengq.rabbitmq.producer.Producers;
import com.chengq.chengq.tools.RedisUtil;
import com.chengq.chengq.ulit.ResponseHelper;
import com.chengq.chengq.ulit.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeProvider implements HomeController {
    @Autowired
    Producers rabbitMqProducer;

    @Autowired
    private RedisUtil redisUtil;



    @Override
    public ResponseModel hello(@RequestParam("id") Long id) {
        redisUtil.set("redis", "测试redis专用", 20);

        rabbitMqProducer.send("我是rabbitmq,rabbitmq测试" + id, 10L);

        log.info("=======redis测试结果===========" + redisUtil.get("redis"));
        //        return ResponseHelper.succeed(AccountMapper.selectList(null));
        return ResponseHelper.succeed("hello");
    }

    @Override
    public String test() {
        return "chengq";
    }
}
