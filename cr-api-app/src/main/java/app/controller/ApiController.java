package app.controller;

import app.api.UserApi;
import app.dto.CommonResult;
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

//    @Resource
//    private UserApi userApi;

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public CommonResult register(@RequestParam User user) {
//        return userApi.register(user);
        return null;
    }

    @ApiOperation("登录用户")
    @PostMapping("/login")
    public CommonResult login(@RequestParam User user) {
//        return userApi.login(user);
        return null;
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    public CommonResult<String> update(@RequestParam User user) {
//        return userApi.update(user);
        return null;
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePwd")
    public CommonResult<String> updatePwd(@RequestParam(name = "username", required = false) String username,
                                          @RequestParam(name = "email", required = false) String email,
                                          @RequestParam("password") String newPassword) {
//        return userApi.updatePwd(username, email, newPassword);
        return null;
    }
}
