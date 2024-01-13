package app.reimburse.dto;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * Description：流程节点DTO
 * <p>Date: 2024/1/9
 * <p>Time: 1:40
 *
 * @Author SillyBaka
 **/
public class ProcessNodeDTO {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 在流程线中的排序；用于生成流程图
     */
    private Integer order;

    /**
     * 是否是流程线最后一个节点，1-是， 0-不是
     */
    private Integer isLast;

    /**
     * 经销人id，即需要操作此节点的人员
     */
    private Long oprUser;

    /**
     * 经销人名字
     */
    private String oprUserName;

    /**
     * 节点状态，
     *   -1-通过失败，
     *   0-未操作，
     *   1-已通过
     */
    private Integer state;

    /**
     * 节点类型，0-报销发起、1-业务审批、2-出纳付款、3-流程转发、4-报销完成
     */
    private Integer type;

    /**
     * 节点操作反馈，若失败则返回失败原因，通过则返回通过意见
     */
    private String feedback;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

    public Long getOprUser() {
        return oprUser;
    }

    public void setOprUser(Long oprUser) {
        this.oprUser = oprUser;
    }

    public String getOprUserName() {
        return oprUserName;
    }

    public void setOprUserName(String oprUserName) {
        this.oprUserName = oprUserName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
