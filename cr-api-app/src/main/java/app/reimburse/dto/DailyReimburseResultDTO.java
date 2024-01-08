package app.reimburse.dto;

import app.reimburse.entity.DailySheetInfo;

import java.sql.Date;
import java.util.List;

/**
 * Description：日常报销响应dto
 * <p>Date: 2024/1/8
 * <p>Time: 11:43
 *
 * @Author SillyBaka
 **/
public class DailyReimburseResultDTO {

    private Long id;

    private String name;

    private Integer type;

    private Long applicantId;

    private Double price;

    private Integer state;

    private Long curNodeId;

    private Date createTime;

    private Date finishTime;

    private String relevantProj;

    /**
     * 申请人名字
     */
    private String applicantName;

    /**
     * 当前流程节点类型-常量名
     */
    private String curNodeType;

    /**
     * 当前流程节点操作人id
     */
    private Long curNodeOprUser;

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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getCurNodeType() {
        return curNodeType;
    }

    public void setCurNodeType(String curNodeType) {
        this.curNodeType = curNodeType;
    }


    public Long getCurNodeOprUser() {
        return curNodeOprUser;
    }

    public void setCurNodeOprUser(Long curNodeOprUser) {
        this.curNodeOprUser = curNodeOprUser;
    }

    public List<DailySheetInfo> getDailySheetInfoList() {
        return dailySheetInfoList;
    }

    public void setDailySheetInfoList(List<DailySheetInfo> dailySheetInfoList) {
        this.dailySheetInfoList = dailySheetInfoList;
    }
}
