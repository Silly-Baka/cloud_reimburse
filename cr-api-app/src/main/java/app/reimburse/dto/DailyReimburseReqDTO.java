package app.reimburse.dto;

import java.sql.Date;
import java.util.List;

/**
 * Description：日常报销单请求DTO
 * <p>Date: 2023/12/18
 * <p>Time: 19:48
 *
 * @Author SillyBaka
 **/
public class DailyReimburseReqDTO {

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
     * 报销单标题
     */
    private String name;

    /**
     * 报销单发起日期
     */
    private Date createTime;

    /**
     * 相关项目
     */
    private String relevantProj;

    /**
     * 相关发票列表
     */
    private List<InvoiceResultDTO> relevantInvoiceList;

    /**
     * 报销费用事项列表
     */
    private List<DailySheetInfoReqDTO> dailySheetInfoReqDTOList;

    /**
     * TODO：补充检查逻辑
     * 检查参数
     * @return
     */
    public Boolean checkParam() {
        return true;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRelevantProj() {
        return relevantProj;
    }

    public void setRelevantProj(String relevantProj) {
        this.relevantProj = relevantProj;
    }

    public List<InvoiceResultDTO> getRelevantInvoiceList() {
        return relevantInvoiceList;
    }

    public void setRelevantInvoiceList(List<InvoiceResultDTO> relevantInvoiceList) {
        this.relevantInvoiceList = relevantInvoiceList;
    }

    public List<DailySheetInfoReqDTO> getDailySheetInfoReqDTOList() {
        return dailySheetInfoReqDTOList;
    }

    public void setDailySheetInfoReqDTOList(List<DailySheetInfoReqDTO> dailySheetInfoReqDTOList) {
        this.dailySheetInfoReqDTOList = dailySheetInfoReqDTOList;
    }
}
