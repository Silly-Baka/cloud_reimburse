package app.user.entity;

import app.common.LeafTag;

/**
 * Description：权限
 * <p>Date: 2023/11/21
 * <p>Time: 22:41
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "authority")
public class Authority {

    /**
     * 权限id
     */
    private Long id;

    /**
     * 权限名称
     */
    private String authName;

    /**
     * 权限对应的前端控件的id； 可以访问目标控件及其所有子控件
     */
    private String ctrlId;

    /**
     * 权限对应的请求前缀； 如 /can/request/ 可以请求以/can/request/作为前缀的所有接口
     */
    private String reqPrefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getCtrlId() {
        return ctrlId;
    }

    public void setCtrlId(String ctrlId) {
        this.ctrlId = ctrlId;
    }

    public String getReqPrefix() {
        return reqPrefix;
    }

    public void setReqPrefix(String reqPrefix) {
        this.reqPrefix = reqPrefix;
    }
}
