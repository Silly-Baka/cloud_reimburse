package app.api;

import app.CommonResult;
import app.user.dto.UserDTO;
import app.user.dto.UserQryDTO;
import app.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description：
 * <p>Date: 2023/12/1
 * <p>Time: 22:42
 *
 * @Author SillyBaka
 **/
@FeignClient(name = "api-user", url = "${api.url.api-user}", path = "/user")   //TODO 补充configuration、内部写url、端口等信息
public interface UserApi {

    @ApiOperation("注册用户")
    @PostMapping("/register")
    CommonResult register(@RequestBody User user);

    @ApiOperation("登录用户")
    @PostMapping("/login")
    CommonResult login(@RequestBody User user);

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    CommonResult<String> update(@RequestBody User user);

    @ApiOperation("修改密码")
    @PostMapping("/updatePwd")
    CommonResult<String> updatePwd(@RequestParam(name = "username", required = false) String username,
                                   @RequestParam(name = "email", required = false) String email,
                                   @RequestParam("curPwd") String curPwd,
                                   @RequestParam("newPwd") String newPwd);

    @ApiOperation("条件查询用户信息")
    @GetMapping("/getUserSelective")
    CommonResult<List<UserDTO>> getUserSelective(@RequestBody UserQryDTO qryDTO);

    @ApiOperation("查找指定部门的指定角色人员")
    @GetMapping("/getUserByDeptAndRole")
    CommonResult<List<Long>> getUserByDeptAndRole(@RequestParam("deptName" ) String deptName,
                                                  @RequestParam("roleName") String roleName);

    @ApiOperation("根据id查询用户信息")
    @GetMapping("/{userId}")
    CommonResult<UserDTO> getUserById(@RequestParam("userId") Long userId);
}
