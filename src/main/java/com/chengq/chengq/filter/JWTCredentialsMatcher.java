package com.chengq.chengq.filter;

import com.chengq.chengq.businessmodule.entity.AccountEntity;
import com.chengq.chengq.tools.JwtOperator;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *  CredentialsMatcher
 *  接口由类实现，该类可以确定是否提供了AuthenticationToken凭证与系统中存储的相应帐户的凭证相匹配。
 *  Shiro 加密匹配 重写匹配方法CredentialsMatcher  使用JWTUtil 匹配方式
 */
@Slf4j
public class JWTCredentialsMatcher  implements CredentialsMatcher {

    @Autowired
    private JwtOperator JwtOperator;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        log.info("==================鉴权：4==================");
        return true;
    }
    //    /**
//     * Matcher中直接调用工具包中的verify方法即可
//     */
//    @Override
//    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
//        String token = ((JwtToken)authenticationToken).getToken();
//        String user = (String)authenticationInfo.getPrincipals().getPrimaryPrincipal();
//        //得到DefaultJwtParser
//        Boolean verify = JwtOperator.isTokenExpired(token);
//        log.info("JWT密码效验结果="+verify);
//        return verify;
//    }
}
