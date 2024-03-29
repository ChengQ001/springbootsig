package com.chengq.businessmodule.controller;


import com.chengq.businessmodule.model.account.AccountQuery;
import com.chengq.businessmodule.model.base.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/api/test")
@Api(tags = "测试相关接口")
public interface TestController {

    @ApiOperation(value = "分页相关")
    @PostMapping("/getMyPage")
    ResponseModel getMyPage(@RequestBody @Valid AccountQuery query);

    @ApiOperation(value = "测试队列以及redis")
    @GetMapping("/hello")
    ResponseModel hello(@RequestParam("id") Long id);

}
