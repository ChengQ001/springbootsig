package com.chengq;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// @EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@Slf4j
public class ChengqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChengqApplication.class, args);
        log.info("=============启动成功======================");
        log.info("=====接口文档:http://localhost:3350/doc.html");
    }
}


