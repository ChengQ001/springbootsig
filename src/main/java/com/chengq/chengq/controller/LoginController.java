package com.chengq.chengq.controller;


import com.chengq.chengq.entity.AccountEntity;
import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.service.AccountService;
import com.chengq.chengq.tools.ResponseHelper;
import com.chengq.chengq.tools.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/login")
@Api(tags = "登录相关")
public class LoginController {

    @Resource
    AccountService accountService;

    @ApiOperation(value = "查询用户")
    @GetMapping("getUserInfo")
    public ResponseModel getUserInfo(@RequestParam("id") Integer id) {
        AccountEntity entity = accountService.getModel(id);

        return ResponseHelper.succeed(entity);
    }


    @ApiOperation(value = "分页用户")
    @PostMapping("getPage")
    public ResponseModel getPage(@RequestBody AccountQuery req) {
        return ResponseHelper.succeed(accountService.getPage(req));
    }
}
