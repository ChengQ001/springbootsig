package com.chengq.chengq.controller;


import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.ulit.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/login")
@Api(tags = "2登录相关")
public interface LoginController {
    @ApiOperation(value = "查询用户")
    @GetMapping("/getUserInfo")
    ResponseModel getUserInfo(@RequestParam("id") Integer id);



    @ApiOperation(value = "获取token")
    @PostMapping("/getToken")
    ResponseModel getToken(@RequestParam("userInfo")String userInfo);

    @ApiOperation(value = "获取getTokenInfo")
    @PostMapping("/getTokenInfo")
    ResponseModel getTokenInfo(@RequestParam("token")String token);

    @ApiOperation(value = "分页")
    @PostMapping("/getMyPage")
    ResponseModel getMyPage(@RequestBody AccountQuery query);

}
