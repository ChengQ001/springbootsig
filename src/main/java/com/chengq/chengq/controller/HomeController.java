package com.chengq.chengq.controller;


import com.chengq.chengq.mapper.AccountMapper;
import com.chengq.chengq.rabbitmq.producer.Producers;
import com.chengq.chengq.tools.RedisUtil;
import com.chengq.chengq.tools.ResponseHelper;
import com.chengq.chengq.tools.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/home")
@Api(tags = "我的Home接口")
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AccountMapper AccountMapper;

    @Autowired
    Producers rabbitMqProducer;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "hello接口")
    @GetMapping("hello")
    public ResponseModel hello(@RequestParam("id") Long id) {
        redisUtil.set("redis", "测试redis专用", 20);

        rabbitMqProducer.send("我是rabbitmq,rabbitmq测试" + id, 10L);

        logger.info("=======redis测试结果===========" + redisUtil.get("redis"));
        return ResponseHelper.succeed(AccountMapper.selectList(null));
    }
}
