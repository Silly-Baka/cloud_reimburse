package app.controller;

import app.common.CommonResult;
import app.user.dto.UserDTO;
import app.user.dto.UserQryDTO;
import app.user.entity.User;
import app.service.UserService;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description：用户模块controller
 * <p>Date: 2023/12/1
 * <p>Time: 22:02
 *
 * @Author SillyBaka
 **/
//DONE 待实现方法逻辑
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("注册用户")
    @PostMapping("/user/register")
    public CommonResult register(@RequestBody User user) {

        //TODO：检验user的合法性

        boolean flag = userService.register(user);
        if(!flag) {
            return CommonResult.fail(400, "注册新用户失败，请检查格式");
        }
        return CommonResult.ok();
    }

    @ApiOperation("登录用户")
    @PostMapping("/user/login")
    public CommonResult login(@RequestBody User user) {
        String username = user.getUsername();
        if(StrUtil.isBlank(username)) {
            return CommonResult.fail(400, "用户名不可为空");
        }
        String password = user.getPassword();
        if(StrUtil.isBlank(password)) {
            return CommonResult.fail(400, "密码不可为空");
        }

        UserDTO result = userService.login(username, password);
        if(result == null) {
            return CommonResult.fail(401, "密码不匹配，登录失败");
        }

        return CommonResult.ok(result);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/user/update")
    public CommonResult update(@RequestBody User user) {
        boolean result = userService.updateById(user);

        if(!result) {
            return CommonResult.fail(200, "更新失败，不存在此用户");
        }
        return CommonResult.ok();
    }

    @ApiOperation("修改密码")
    @PostMapping("/user/updatePwd")
    public CommonResult updatePwd(@RequestParam(name = "username", required = false) String username,
                                          @RequestParam(name = "email", required = false) String email,
                                          @RequestParam("curPwd") String curPwd,
                                          @RequestParam("newPwd") String newPwd) {

        boolean result = userService.updatePwd(username, email, curPwd, newPwd);

        if(!result) {
            return CommonResult.fail(200, "修改密码失败，请重新检查参数");
        }
        return CommonResult.ok();
    }

    @ApiOperation("条件查询用户信息")
    @GetMapping("/user/getUserSelective")
    public CommonResult getUserSelective(@RequestParam UserQryDTO qryDTO) {
        //TODO：补充逻辑
        return null;
    }

    @ApiOperation("查找指定部门的指定角色人员ID")
    @GetMapping("/user/getUserByDeptAndRole")
    public CommonResult getUserByDeptAndRole(@RequestParam("deptName") String deptName,
                                             @RequestParam("roleName") String roleName) {
        //DONE：补充逻辑
        if(StrUtil.isBlank(deptName) || StrUtil.isBlank(roleName)) {
            return CommonResult.fail(400, "请求参数不能为空");
        }

        return CommonResult.ok(userService.getUserByDeptAndRole(deptName, roleName));
    }

    @ApiOperation("根据id查询用户信息")
    @GetMapping("/user/{userId}")
    public CommonResult getUserById(@PathVariable("userId") Long userId) {

        if(userId == null) {
            return CommonResult.fail(400, "请求参数不能为空");
        }
        return CommonResult.ok(userService.getUserById(userId));
    }
}
