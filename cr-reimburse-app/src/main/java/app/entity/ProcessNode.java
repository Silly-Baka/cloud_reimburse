package app.entity;

/**
 * Description：流程节点
 * <p>Date: 2023/11/21
 * <p>Time: 22:32
 *
 * @Author SillyBaka
 **/
public class ProcessNode {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 所属报销单id
     */
    private Long sheetId;

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

    public Long getSheetId() {
        return sheetId;
    }

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
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
