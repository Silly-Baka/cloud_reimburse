package app.entity;

/**
 * Description：用户角色关联表
 * <p>Date: 2023/12/12
 * <p>Time: 22:17
 *
 * @Author SillyBaka
 **/
public class UserRoleRel {

    private Long id;

    private Long userId;

    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
