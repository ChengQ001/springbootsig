package com.chengq.chengq.provider;

import com.chengq.chengq.controller.LoginController;
import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.service.AccountService;
import com.chengq.chengq.tools.JwtOperator;
import com.chengq.chengq.ulit.ResponseHelper;
import com.chengq.chengq.ulit.ResponseModel;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginProvider implements LoginController {
    @Autowired
    AccountService accountService;

    @Autowired
    private JwtOperator jwtOperator;


    @Override
    public ResponseModel getUserInfo(Integer id) {
        return  ResponseHelper.succeed(accountService.getModel(id));
    }

    @Override
    public ResponseModel getToken(String userInfo) {
        return ResponseHelper.succeed(jwtOperator.geneJsonWebToken(userInfo));
    }

    @Override
    public ResponseModel getTokenInfo(String token) {

        Claims claims = jwtOperator.checkJWT(token);
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
        return ResponseHelper.succeed(accountService.getMyPage(query));
    }
}
