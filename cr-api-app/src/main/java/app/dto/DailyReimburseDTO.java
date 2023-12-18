package app.dto;

import app.entity.DailySheetInfo;

import java.sql.Date;
import java.util.List;

/**
 * Description：日常报销单DTO
 * <p>Date: 2023/12/18
 * <p>Time: 19:48
 *
 * @Author SillyBaka
 **/
public class DailyReimburseDTO {
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
    private final Integer type = 1;

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
     * 报销费用事项列表
     */
    private List<DailySheetInfo> dailySheetInfoList;

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

    public List<DailySheetInfo> getDailySheetInfoList() {
        return dailySheetInfoList;
    }

    public void setDailySheetInfoList(List<DailySheetInfo> dailySheetInfoList) {
        this.dailySheetInfoList = dailySheetInfoList;
    }
}