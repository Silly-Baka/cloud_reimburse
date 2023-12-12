package app.api;

import app.dto.CommonResult;
import app.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description：
 * <p>Date: 2023/12/1
 * <p>Time: 22:42
 *
 * @Author SillyBaka
 **/
@FeignClient(name = "api-user")   //TODO 补充configuration、内部写url、端口等信息
public interface UserApi {

    @ApiOperation("注册用户")
    @PostMapping("/register")
    CommonResult register(@RequestParam User user);

    @ApiOperation("登录用户")
    @PostMapping("/login")
    CommonResult login(@RequestParam User user);

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    CommonResult<String> update(@RequestParam User user);

    @ApiOperation("修改密码")
    @PostMapping("/updatePwd")
    CommonResult<String> updatePwd(@RequestParam(name = "username", required = false) String username,
                                   @RequestParam(name = "email", required = false) String email,
                                   @RequestParam("curPwd") String curPwd,
                                   @RequestParam("newPwd") String newPwd);
}
