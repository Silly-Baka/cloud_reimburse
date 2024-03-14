package app.user.dto;

/**
 * Description：
 * <p>Date: 2024/3/13
 * <p>Time: 21:09
 *
 * @Author SillyBaka
 **/
public class UpdateUserRoleReqDTO {

    private Long userId;

    private Long roleId;

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
