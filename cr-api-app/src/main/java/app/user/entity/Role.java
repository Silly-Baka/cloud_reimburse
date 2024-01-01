package app.user.entity;

import app.common.LeafTag;

/**
 * Description：角色
 * <p>Date: 2023/11/21
 * <p>Time: 22:40
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "role")

public class Role {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 父级角色id列表，以逗号分隔； 举例：报销人员同样也是普通员工，拥有普通员工的权限，所以普通员工是报销人员的父角色
     */
    private String parentIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
