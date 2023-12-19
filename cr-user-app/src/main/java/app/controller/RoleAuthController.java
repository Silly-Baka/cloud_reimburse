package app.controller;

import app.common.CommonResult;
import app.user.entity.Authority;
import app.service.AuthService;
import app.service.RoleAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description：权限管理模块controller
 * <p>Date: 2023/12/12
 * <p>Time: 22:01
 *
 * @Author SillyBaka
 **/
//TODO 该模块下所有接口需鉴权，最高管理员才可执行
@RestController("/auth")
public class RoleAuthController {

    @Resource
    private RoleAuthService roleAuthService;
    @Resource
    private AuthService authService;


    @ApiOperation("增加权限单元，目前仅供开发使用")
    @PostMapping("/addAuth")
    public CommonResult addAuth(@RequestParam("authority") Authority authority) {

        boolean result = authService.save(authority);
        if(!result) {
            return CommonResult.fail(200, "增加失败");
        }

        return CommonResult.ok();
    }

    @ApiOperation("为指定用户设置角色")
    @PostMapping("/setRole")
    public CommonResult setRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {

        Boolean result = roleAuthService.setRole(userId, roleId);
        if(!result) {
            return CommonResult.fail(200, "为用户设置角色失败");
        }
        return CommonResult.ok();
    }

    @ApiOperation("为指定角色添加权限")
    @PostMapping("/addRoleAuth")
    public CommonResult addRoleAuth(@RequestParam("roleId") Long roleId, @RequestParam("authId") Long authId) {

        Boolean result = roleAuthService.addRoleAuth(roleId, authId);
        if(!result) {
            return CommonResult.fail(200, "为角色设定权限失败");
        }
        return CommonResult.ok();
    }
}
