package app.reimburse.dto;

import java.sql.Date;

/**
 * Description：日常费用事项请求dto
 * <p>Date: 2023/12/19
 * <p>Time: 14:50
 *
 * @Author SillyBaka
 **/
public class DailySheetInfoReqDTO {

    /**
     * 费用日期
     */
    private Date costDate;

    /**
     * 费用类型
     */
    //TODO：待处理常量枚举值
    private Integer costType;

    /**
     * 费用说明
     */
    private String description;

    /**
     * 对应发票id
     */
    private Long invoiceId;

    /**
     * 报销金额
     */
    private Double price;

    public Date getCostDate() {
        return costDate;
    }

    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
