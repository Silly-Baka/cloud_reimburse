package app.api;

import app.dto.CommonResult;
import app.entity.Authority;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description：角色权限管理模块api
 * <p>Date: 2023/12/12
 * <p>Time: 23:02
 *
 * @Author SillyBaka
 **/
@FeignClient(name = "api-role_auth")
public interface RoleAuthApi {
    @ApiOperation("增加权限单元，目前仅供开发使用")
    @PostMapping("/addAuth")
    CommonResult addAuth(@RequestParam Authority authority);

    @ApiOperation("为指定用户设置角色")
    @PostMapping("/setRole")
    CommonResult setRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId);

    @ApiOperation("为指定角色添加权限")
    @PostMapping("/addRoleAuth")
    CommonResult addRoleAuth(@RequestParam("roleId") Long roleId, @RequestParam("authId") Long authId);
}
