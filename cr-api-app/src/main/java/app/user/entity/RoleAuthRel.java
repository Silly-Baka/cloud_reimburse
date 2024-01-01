package app.user.entity;

import app.common.LeafTag;

/**
 * Description：角色权限关联表
 * <p>Date: 2023/12/12
 * <p>Time: 22:18
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "roleAuthRel")
public class RoleAuthRel {

    private Long id;

    private Long roleId;

    private Long authId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }
}
