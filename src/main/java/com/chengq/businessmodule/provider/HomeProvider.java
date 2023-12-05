package com.chengq.businessmodule.provider;

import com.chengq.Enums.CoreExceptionEnum;
import com.chengq.businessmodule.controller.HomeController;

import com.chengq.businessmodule.service.UserBean;
import com.chengq.businessmodule.service.UserService;
import com.chengq.exception.ServiceException;
import com.chengq.businessmodule.model.base.ResponseHelper;
import com.chengq.businessmodule.model.base.ResponseModel;
import com.chengq.ulit.helper.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
