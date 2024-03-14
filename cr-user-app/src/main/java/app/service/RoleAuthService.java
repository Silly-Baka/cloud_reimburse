package app.service;



import app.user.entity.Role;

import java.util.List;

/**
 * Description：角色权限模块的service层
 * <p>Date: 2023/12/12
 * <p>Time: 22:21
 *
 * @Author SillyBaka
 **/
public interface RoleAuthService {

    /**
     * 为指定用户设定角色
     * @param userId 用户id
     * @param roleId 角色id
     */
    Boolean setRole(Long userId, Long roleId);

    /**
     * 为指定角色添加权限
     * @param roleId 角色id
     * @param authId 权限id
     */
    Boolean addRoleAuth(Long roleId, Long authId);

    List<Role> getRoleList();

    Role getRoleByUserId(Long userId);
}
