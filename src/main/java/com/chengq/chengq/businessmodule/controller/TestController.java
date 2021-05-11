package com.chengq.chengq.businessmodule.controller;


import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.ulit.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/api/test")
@Api(tags = "测试相关接口")
public interface TestController {
    @ApiOperation(value = "测试查询用户")
    @GetMapping("/getUserInfo")
    ResponseModel getUserInfo(@RequestParam("id") Integer id);

    @ApiOperation(value = "测试获取token")
    @PostMapping("/getToken")
    @RequiresPermissions("角色A")  //添加需要授权的标签 不需要授权不用打 只需要设置路由匿名
    ResponseModel getToken(@RequestParam("userInfo")String userInfo);

    @ApiOperation(value = "测试获取getTokenInfo")
    @PostMapping("/getTokenInfo")
    ResponseModel getTokenInfo(@RequestParam("token")String token);

    @ApiOperation(value = "测试分页")
    @PostMapping("/getMyPage")
    ResponseModel getMyPage(@RequestBody @Valid AccountQuery query);

    @ApiOperation(value = "测试队列以及redis")
    @GetMapping("/hello")
    ResponseModel hello(@RequestParam("id") Long id);

}
