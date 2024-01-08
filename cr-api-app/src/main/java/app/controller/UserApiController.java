package app.controller;

import app.api.RoleAuthApi;
import app.api.UserApi;
import app.CommonResult;
import app.user.entity.Authority;
import app.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description：api网关的controller，负责user模块的请求
 * <p>Date: 2023/11/21
 * <p>Time: 20:10
 *
 * @Author SillyBaka
 **/
@RestController
public class UserApiController {

    @Resource
    private UserApi userApi;
    @Resource
    private RoleAuthApi roleAuthApi;

    @ApiOperation("注册用户")
    @PostMapping("/api/v1/user/register")
    public CommonResult register(@RequestBody User user) {
        return userApi.register(user);
    }

    @ApiOperation("登录用户")
    @PostMapping("/api/v1/user/login")
    public CommonResult login(@RequestBody User user) {
        return userApi.login(user);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/api/v1/user/update")
    public CommonResult<String> update(@RequestBody User user) {
        return userApi.update(user);
    }

    @ApiOperation("修改密码")
    @PostMapping("/api/v1/user/updatePwd")
    public CommonResult<String> updatePwd(@RequestParam(name = "username", required = false) String username,
                                          @RequestParam(name = "email", required = false) String email,
                                          @RequestParam("curPwd") String curPwd,
                                          @RequestParam("newPwd") String newPwd) {
        return userApi.updatePwd(username, email, curPwd, newPwd);
    }



    @ApiOperation("增加权限单元，目前仅供开发使用")
    @PostMapping("/api/v1/user/addAuth")
    public CommonResult addAuth(@RequestBody Authority authority) {
        return roleAuthApi.addAuth(authority);
    }

    @ApiOperation("为指定用户设置角色")
    @PostMapping("/api/v1/user/setRole")
    public CommonResult setRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {

        return roleAuthApi.setRole(userId, roleId);
    }

    @ApiOperation("为指定角色添加权限")
    @PostMapping("/api/v1/user/addRoleAuth")
    public CommonResult addRoleAuth(@RequestParam("roleId") Long roleId, @RequestParam("authId") Long authId) {

        return roleAuthApi.addRoleAuth(roleId, authId);
    }

}
