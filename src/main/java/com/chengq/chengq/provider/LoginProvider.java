package com.chengq.chengq.provider;

import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.chengq.chengq.controller.LoginController;
import com.chengq.chengq.entity.AccountEntity;
import com.chengq.chengq.exception.SigExceptionEnum;
import com.chengq.chengq.model.account.AccountPageReq;
import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.service.AccountService;
import com.chengq.chengq.tools.JwtOperator;
import com.chengq.chengq.tools.ResponseHelper;
import com.chengq.chengq.tools.ResponseModel;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LoginProvider implements LoginController {
    @Autowired
    AccountService accountService;

    @Autowired
    private JwtOperator jwtOperator;

    @Override
    public ResponseModel getUserInfo(@RequestParam("id") Integer id) {
        AccountEntity entity = accountService.getModel(id);

        return ResponseHelper.succeed(entity);
    }


    @Override
    public ResponseModel getPage(@RequestBody AccountQuery req) {
        return ResponseHelper.succeed(accountService.getPage(req));
    }

    @Override
    public ResponseModel getMyPage(@RequestBody AccountPageReq req) {
        return ResponseHelper.succeed(accountService.getMyPage(req));
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
           throw new ServiceException(SigExceptionEnum.ERROR_TOKEN);
        }
        return ResponseHelper.succeed(id);
    }
}
