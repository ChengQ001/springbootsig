package com.chengq.chengq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.chengq.chengq.mapper")
@SpringBootApplication
public class ChengqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChengqApplication.class, args);
    }

}
