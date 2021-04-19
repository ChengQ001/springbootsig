package com.chengq.chengq;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.chengq.chengq.businessmodule.mapper.mapping")
@EnableTransactionManagement(proxyTargetClass=true)
@SpringBootApplication
@Slf4j
public class ChengqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChengqApplication.class, args);
        log.info("=============启动成功======================");
    }

}
