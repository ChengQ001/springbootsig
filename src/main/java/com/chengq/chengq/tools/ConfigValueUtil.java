package com.chengq.chengq.tools;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "config")
public class ConfigValueUtil {

    /**
     * 项目环境 业务
     */
    @Value("${spring.profiles.active}")
    private String environment;
    /**
     * 秘钥jwt
     * -
     */
    @Value("${jwt.secret}")
   private String secret;
    /**
     * 有效期，单位秒
     * - 默认2周
     */
    @Value("${jwt.expiration}")
    private long expirationTimeInSecond;
    /**
     * swagger开关
     */
    @Value("${swagger.enabled}")
    private Boolean swaggerEnable;
    /**
     * jwt前缀
     */
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;
}
