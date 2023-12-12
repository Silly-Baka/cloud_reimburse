package app.controller;

import app.api.RoleAuthApi;
import app.api.UserApi;
import app.dto.CommonResult;
import app.entity.Authority;
import app.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description：api网关的controller，接收来自外部的所有请求并转发
 * <p>Date: 2023/11/21
 * <p>Time: 20:10
 *
 * @Author SillyBaka
 **/
@RestController("/api/v1")
public class ApiController {

    @Resource
    private UserApi userApi;
    @Resource
    private RoleAuthApi roleAuthApi;

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public CommonResult register(@RequestParam User user) {
        return userApi.register(user);
    }

    @ApiOperation("登录用户")
    @PostMapping("/login")
    public CommonResult login(@RequestParam User user) {
        return userApi.login(user);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    public CommonResult<String> update(@RequestParam User user) {
        return userApi.update(user);
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePwd")
    public CommonResult<String> updatePwd(@RequestParam(name = "username", required = false) String username,
                                          @RequestParam(name = "email", required = false) String email,
                                          @RequestParam("curPwd") String curPwd,
                                          @RequestParam("newPwd") String newPwd) {
        return userApi.updatePwd(username, email, curPwd, newPwd);
    }



    @ApiOperation("增加权限单元，目前仅供开发使用")
    @PostMapping("/addAuth")
    public CommonResult addAuth(@RequestParam Authority authority) {
        return roleAuthApi.addAuth(authority);
    }

    @ApiOperation("为指定用户设置角色")
    @PostMapping("/setRole")
    public CommonResult setRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {

        return roleAuthApi.setRole(userId, roleId);
    }

    @ApiOperation("为指定角色添加权限")
    @PostMapping("/addRoleAuth")
    public CommonResult addRoleAuth(@RequestParam("roleId") Long roleId, @RequestParam("authId") Long authId) {

        return roleAuthApi.addRoleAuth(roleId, authId);
    }

}
