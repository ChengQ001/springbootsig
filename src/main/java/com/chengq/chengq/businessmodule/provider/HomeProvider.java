package com.chengq.chengq.businessmodule.provider;

import com.chengq.chengq.Enums.CoreExceptionEnum;
import com.chengq.chengq.businessmodule.controller.HomeController;
import com.chengq.chengq.businessmodule.controller.TestController;
import com.chengq.chengq.businessmodule.service.AccountService;
import com.chengq.chengq.database.UserBean;
import com.chengq.chengq.database.UserService;
import com.chengq.chengq.exception.ServiceException;
import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.rabbitmq.producer.Producers;
import com.chengq.chengq.tools.JWTUtil;
import com.chengq.chengq.tools.RedisUtil;
import com.chengq.chengq.ulit.ResponseHelper;
import com.chengq.chengq.ulit.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class HomeProvider implements HomeController {


    @Autowired
    UserService userService;
    @Override
    public ResponseModel login(String username, String password) {
        UserBean userBean = userService.getUser(username);
        if (userBean.getPassword().equals(password)) {
            return ResponseHelper.succeed(JWTUtil.sign(username, password));
        } else {
            throw new ServiceException(CoreExceptionEnum.ALI_OSS_ERROR);
        }
    }

    @Override
    public ResponseModel article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return ResponseHelper.succeed( "","You are already logged in","200");
        } else {
            return ResponseHelper.succeed( "","You are guest","200");
        }
    }

    @Override
    public ResponseModel requireAuth() {
        return ResponseHelper.succeed( "","You are authenticated","200");
    }

    @Override
    public ResponseModel requireRole() {
        return ResponseHelper.succeed( "","You are visiting require_role","200");
    }

    @Override
    public ResponseModel requirePermission() {
        return ResponseHelper.succeed( "","You are visiting requirePermission","200");
    }

    @Override
    public ResponseModel test() {
        return ResponseHelper.succeed( userService.test());
    }
}
