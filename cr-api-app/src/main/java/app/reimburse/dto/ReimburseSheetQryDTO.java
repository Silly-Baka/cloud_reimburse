package app.reimburse.dto;

import java.util.Date;

/**
 * Description：用于报销单条件查询的DTO
 * <p>Date: 2024/1/2
 * <p>Time: 21:08
 *
 * @Author SillyBaka
 **/
public class ReimburseSheetQryDTO {

    /**
     * 发起者id
     */
    private Long applicantId;

    /**
     * 报销类型
     */
    private Integer type;

    /**
     * 开始查询日期
     */
    private Date startDate;

    /**
     * 结束查询日期
     */
    private Date endDate;

    /**
     * 报销流程状态
     */
    private Integer state;

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
