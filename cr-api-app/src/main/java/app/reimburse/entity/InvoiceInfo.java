package app.reimburse.entity;

import app.common.LeafTag;

/**
 * Description：发票详情记录
 * <p>Date: 2023/11/21
 * <p>Time: 22:28
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "invoiceInfo")
public class InvoiceInfo {
    /**
     * 唯一标识
     */
    private Long id;

    /**
     * 所属发票id
     */
    private Long invId;

    /**
     * 货物或应税劳务、服务名称
     */
    private String name;

    /**
     * 规格型号，可为空
     */
    private String spec;

    /**
     * 计数单位
     */
    private String unit;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 总金额
     */
    private Double totalPrice;

    /**
     * 税率
     */
    private Double taxRate;

    /**
     * 税额
     */
    private Double taxPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }
}
