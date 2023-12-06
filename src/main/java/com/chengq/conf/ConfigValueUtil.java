package com.chengq.conf;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
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
    @Value("${swagger.enabled:false}")
    private Boolean swaggerEnable;
    /**
     * jwt前缀
     */
    @Value("${jwt.tokenPrefix:prefix}")
    private String tokenPrefix;
}
