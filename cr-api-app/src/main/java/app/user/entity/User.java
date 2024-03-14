package app.user.entity;

import app.common.LeafTag;

/**
 * Description：用户信息
 * <p>Date: 2023/11/21
 * <p>Time: 22:38
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "user")
public class User {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名，用于登陆
     */
    private String username;

    /**
     * 用户密码，加密后存放
     */
    private String password;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 所属部门名字
     */
    private String deptName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像对应文件id
     */
    private Long headerId;

    /**
     * 账号状态
     */
    private Boolean state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
