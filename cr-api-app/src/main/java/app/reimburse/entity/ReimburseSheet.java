package app.reimburse.entity;

import app.common.LeafTag;
import com.baomidou.mybatisplus.annotation.TableField;

import java.sql.Date;

/**
 * Description：报销单基础信息
 * <p>Date: 2023/11/21
 * <p>Time: 22:34
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "reimburseSheet")
public class ReimburseSheet {

    /**
     * 唯一标识
     */
    private Long id;

    /**
     * 报销单名称；一般由报销类型+报销人+日期组成
     */
    private String name;

    /**
     * 报销类型；
     *  0-差旅费，
     *  1-日常费用，
     *  2-业务招待费用
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 报销申请人用户id
     */
    private Long applicantId;

    /**
     * 报销金额
     */
    private Double price;

    /**
     * 报销状态。
     *  -1-审批失败，
     *  0-审批中，
     *  1-审批通过
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 目前所处流程节点的id
     */
    private Long curNodeId;

    /**
     * 报销单发起日期
     */
    private Date createTime;

    /**
     * 报销单完成日期
     */
    private Date finishTime;

    /**
     * 相关项目
     */
    private String relevantProj;

    /**
     * 支付状态，0-未支付，1-已支付
     */
    private Integer payState;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(Long curNodeId) {
        this.curNodeId = curNodeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getRelevantProj() {
        return relevantProj;
    }

    public void setRelevantProj(String relevantProj) {
        this.relevantProj = relevantProj;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }
}
