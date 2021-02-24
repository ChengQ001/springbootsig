package com.chengq.chengq.tools;


import cn.hutool.core.date.DateUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtOperator {
    public String SUBJECT = "admin";
    /**
     * 秘钥
     * -
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * 有效期，单位秒
     * - 默认2周
     */
    @Value("${jwt.expiration}")
    private Long expirationTimeInSecond;


    /**
     * 生成jwt
     *
     * @param userInfo
     * @return
     */
    public String geneJsonWebToken(String userInfo) {

        if (ToolUtil.isEmpty(userInfo)) {
            return null;
        }
        long nowMillis = System.currentTimeMillis();

        Date issuedAt = new Date(nowMillis);
        System.out.println("现在时间:++++++++++:" + DateUtil.format(issuedAt,"yyyy-MM-dd HH:mm:ss"));
        Date expiration = new Date(nowMillis + expirationTimeInSecond.longValue()*1000);
        System.out.println("到期时间:++++++++++:" + DateUtil.format(expiration,"yyyy-MM-dd HH:mm:ss"));
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", userInfo)
                //                .claim("name", userInfo.getUserName())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return token;
    }


    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(secret).
                    parseClaimsJws(token).getBody();
            return claims;

        } catch (Exception e) {

        }
        return null;
    }

}
