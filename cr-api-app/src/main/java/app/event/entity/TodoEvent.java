package app.event.entity;

import java.sql.Date;

/**
 * Description：待办事件
 * <p>Date: 2023/11/21
 * <p>Time: 22:42
 *
 * @Author SillyBaka
 **/
public class TodoEvent {

    /**
     * 事件id
     */
    private Long id;

    /**
     * 事件类型，1-业务审批、2-出纳付款
     */
    private Integer type;

    /**
     * 事件目标报销单id
     */
    private Long sheetId;

    /**
     * 需处理此事件的用户
     */
    private Long todoUser;

    /**
     * 事件状态，0-未处理，1-已处理，2-被督办，3-已超时
     */private Integer state;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 发起时间
     */
    private Date createTime;

    /**
     * 预期最终完成时间，若逾期还未完成，则定期发起站内信通知
     */
    private Date expectTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSheetId() {
        return sheetId;
    }

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }

    public Long getTodoUser() {
        return todoUser;
    }

    public void setTodoUser(Long todoUser) {
        this.todoUser = todoUser;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
