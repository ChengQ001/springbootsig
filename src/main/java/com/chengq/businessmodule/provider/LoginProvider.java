package com.chengq.businessmodule.provider;

import com.chengq.businessmodule.controller.TestController;
import com.chengq.businessmodule.model.account.AccountQuery;
import com.chengq.rabbitmq.producer.Producers;
import com.chengq.businessmodule.service.AccountService;
import com.chengq.businessmodule.model.base.ResponseHelper;
import com.chengq.businessmodule.model.base.ResponseModel;
import com.chengq.ulit.helper.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class LoginProvider implements TestController {
    @Autowired
    AccountService service;
    @Autowired
    Producers rabbitMqProducer;

    @Autowired
    private RedisUtil redisUtil;




    @Override
    public ResponseModel getMyPage(AccountQuery query) {
        return ResponseHelper.succeed(service.getMyPage(query));
    }

    @Override
    public ResponseModel hello(@RequestParam("id") Long id) {
        redisUtil.set("redis", "测试redis专用", 20);

        rabbitMqProducer.sendDelay("我是rabbitmq,rabbitmq测试" + id, id.intValue());

        log.info("=======redis测试结果===========" + redisUtil.get("redis"));
        //        return ResponseHelper.succeed(AccountMapper.selectList(null));
        return ResponseHelper.succeed("hello");
    }
}
