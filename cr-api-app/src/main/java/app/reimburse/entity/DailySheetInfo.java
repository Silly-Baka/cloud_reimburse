package app.reimburse.entity;

import app.common.LeafTag;

import java.sql.Date;

/**
 * Description：日常报销单信息
 * <p>Date: 2023/12/18
 * <p>Time: 19:44
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "dailySheetInfo")
public class DailySheetInfo {

    /**
     * 唯一标识
     */
    private Long id;

    /**
     * 对应的报销单id
     */
    private Long sheetId;

    /**
     * 费用日期
     */
    private Date costDate;

    /**
     * 费用类型，0-设备使用成本
     *         1-日常用品采购成本
     *         2-培训课程费用
     *         3-电话费
     *         4-交通费
     */
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
