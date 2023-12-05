package com.chengq.businessmodule.controller;


import com.chengq.businessmodule.model.base.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/home")
@Api(tags = "登录权限相关接口")
public interface HomeController {

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    ResponseModel login(@RequestParam("username") String username,
                        @RequestParam("password") String password);

    @ApiOperation(value = "匿名访问")
    @GetMapping("/article")
    ResponseModel article();


    @ApiOperation(value = "测试只需要登录")
    @GetMapping("/require_auth")
    @RequiresAuthentication
    ResponseModel requireAuth();

    @ApiOperation(value = "测试角色授权")
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    ResponseModel requireRole();

    @ApiOperation(value = "测试角色配置资源")
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    ResponseModel requirePermission() ;

    @ApiOperation(value = "测试")
    @GetMapping("/test")
    ResponseModel test();

}
