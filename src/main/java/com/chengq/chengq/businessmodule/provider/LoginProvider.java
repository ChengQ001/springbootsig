package com.chengq.chengq.businessmodule.provider;

import com.chengq.chengq.businessmodule.controller.TestController;
import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.rabbitmq.producer.Producers;
import com.chengq.chengq.businessmodule.service.AccountService;
import com.chengq.chengq.tools.JwtOperator;
import com.chengq.chengq.tools.RedisUtil;
import com.chengq.chengq.ulit.ResponseHelper;
import com.chengq.chengq.ulit.ResponseModel;
import io.jsonwebtoken.Claims;
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
    @Autowired
    private JwtOperator JwtOperator;

    @Override
    public ResponseModel getUserInfo(Integer id) {
        return ResponseHelper.succeed(service.getModel(id));
    }

    @Override
    public ResponseModel getToken(String userInfo) {
        return ResponseHelper.succeed(JwtOperator.geneJsonWebToken(userInfo));
    }

    @Override
    public ResponseModel getTokenInfo(String token) {

        Claims claims = JwtOperator.checkJWT(token);
        String id = "";
        if (claims != null) {
            id = (String) claims.get("id");
        } else {
            id = "kong";
        }
        return ResponseHelper.succeed(id);
    }

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
