package com.chengq.chengq.tools;


import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;


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
    private long expirationTimeInSecond;


    /**
     * 生成jwt
     *
     * @param userInfo
     * @return
     */
    public String geneJsonWebToken(String userInfo) {

        //        if (ToolUtil.isEmpty(userInfo)) {
        //            return null;
        //        }
        //        long nowMillis = System.currentTimeMillis();
        //        Date issuedAt = new Date(nowMillis);
        //
        //
        //        SecretKey secretKey = createSecretKey();
        //
        //
        //        String token = Jwts.builder().setSubject(SUBJECT)
        //                .claim("id", userInfo)
        //                //                .claim("name", userInfo.getUserName())
        //                .setIssuedAt(issuedAt)
        //                .setExpiration(expiration)
        //                .signWith(SignatureAlgorithm.HS256, secret).compact();
        //        return token;

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt
        // 已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        //        Map<String,Object> claims = new HashMap<String,Object>();
        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                //                .setClaims(claims)
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .claim("id", userInfo)
                .setId(UUID.randomUUID().toString())                  //设置jti(JWT ID)
                // ：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setSubject(SUBJECT)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid
                // 之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, secret);//设置签名使用的签名算法和签名使用的秘钥
        //设置过期时间
        if (expirationTimeInSecond >= 0) {
            long expMillis = nowMillis + expirationTimeInSecond * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        String newToken = "jwt_" + builder.compact();
        return newToken;

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
                    parseClaimsJws(token.replace("jwt_", "")).getBody();

            String date = DateUtil.format(claims.getExpiration(), "yyyy-MM-dd HH:mm:ss");
            System.out.println("有效期为：" + date);
            return claims;

        } catch (Exception e) {

        }
        return null;
    }

    /**
     * <pre>
     *  验证token是否失效
     *  true:过期   false:没过期
     * </pre>
     */
    public Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    /**
     * 获取jwt的payload部分
     */
    public Claims getClaimFromToken(String token) {
        //        SecretKey secretKey = secret;
        return Jwts.parser()   //得到DefaultJwtParser
                .setSigningKey(secret)  //设置签名的秘钥
                .parseClaimsJws(token.replace("jwt_", ""))
                .getBody();
    }


}
