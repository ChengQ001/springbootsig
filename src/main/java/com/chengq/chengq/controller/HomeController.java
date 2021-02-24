package com.chengq.chengq.controller;



import com.chengq.chengq.tools.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/api/home")
@Api(tags = "1我的Home接口")
public interface HomeController {


    @ApiOperation(value = "hello接口")
    @GetMapping("/hello")
    ResponseModel hello(@RequestParam("id") Long id);

    @ApiOperation(value = "test接口")
    @GetMapping("/test")
    String test();
}
