package app.event.dto;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * Description：待办事件查询dto
 * <p>Date: 2024/1/28
 * <p>Time: 18:09
 *
 * @Author SillyBaka
 **/
public class TodoEventQryDTO {

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 事件类型，1-业务审批、2-出纳付款
     */
    private Integer type;

    /**
     * 需处理此事件的用户
     */
    private Long todoUser;

    /**
     * 事件状态，0-未处理，1-已处理，2-被督办，3-已超时
     */
    private Integer state;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
