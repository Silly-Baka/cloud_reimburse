package app.controller;

import app.common.CommonResult;
import app.dto.UserDTO;
import app.entity.User;
import app.service.UserService;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description：
 * <p>Date: 2023/12/1
 * <p>Time: 22:02
 *
 * @Author SillyBaka
 **/
//TODO 待实现方法逻辑
@RestController("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public CommonResult register(@RequestParam User user) {

        //TODO：检验user的合法性

        boolean flag = userService.register(user);
        if(!flag) {
            return CommonResult.fail(400, "注册新用户失败，请检查格式");
        }
        return CommonResult.ok(null);
    }

    @ApiOperation("登录用户")
    @PostMapping("/login")
    public CommonResult login(@RequestParam User user) {
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
    @PostMapping("/update")
    public CommonResult<String> update(@RequestParam User user) {
        return null;
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePwd")
    public CommonResult<String> updatePwd(@RequestParam(name = "username", required = false) String username,
                                          @RequestParam(name = "email", required = false) String email,
                                          @RequestParam("password") String newPassword) {
        return null;
    }

}
