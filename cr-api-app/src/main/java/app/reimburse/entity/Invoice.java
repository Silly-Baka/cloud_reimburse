package app.reimburse.entity;


import app.common.LeafTag;

/**
 * Description：发票，包含发票基础信息
 * <p>Date: 2023/11/21
 * <p>Time: 21:15
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "invoice")
public class Invoice {

    /**
     * 发票id
     */
    private Long id;

    /**
     * 发票持有者id
     */
    private Long owner;

    /**
     * 发票代码
     */
    private String invCode;

    /**
     * 发票号码
     */
    private String invNum;

    /**
     * 发票类型
     *  0-增值税普通发票，
     *  1-增值税专用发票，
     *  2-增值税电子普通发票，
     *  3-增值税电子专用发票
     */
    private Integer invType;

    /**
     * 不算税的总金额
     */
    private Double priceTotal;

    /**
     * 总税额
     */
    private Double taxTotal;

    /**
     * 价税合计
     */
    private Double priceTaxTotal;

    /**
     * 购买方名字
     */
    private String buyName;

    /**
     * 购买方纳税人识别号
     */
    private String buyTaxpayerNum;

    /**
     * 出售方名字
     */
    private String sellName;

    /**
     * 出售方纳税人识别号
     */
    private String sellTaxpayerNum;

    /**
     * 发票文件id
     */
    private Long invFileId;

    /**
     * 是否已报销
     *  0-否，1-报销中，2-已报销
     */
    private Integer isReimbursed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getInvNum() {
        return invNum;
    }

    public void setInvNum(String invNum) {
        this.invNum = invNum;
    }

    public Integer getInvType() {
        return invType;
    }

    public void setInvType(Integer invType) {
        this.invType = invType;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(Double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public Double getPriceTaxTotal() {
        return priceTaxTotal;
    }

    public void setPriceTaxTotal(Double priceTaxTotal) {
        this.priceTaxTotal = priceTaxTotal;
    }

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public String getBuyTaxpayerNum() {
        return buyTaxpayerNum;
    }

    public void setBuyTaxpayerNum(String buyTaxpayerNum) {
        this.buyTaxpayerNum = buyTaxpayerNum;
    }

    public String getSellName() {
        return sellName;
    }

    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public String getSellTaxpayerNum() {
        return sellTaxpayerNum;
    }

    public void setSellTaxpayerNum(String sellTaxpayerNum) {
        this.sellTaxpayerNum = sellTaxpayerNum;
    }

    public Long getInvFileId() {
        return invFileId;
    }

    public void setInvFileId(Long invFileId) {
        this.invFileId = invFileId;
    }

    public Integer getIsReimbursed() {
        return isReimbursed;
    }

    public void setIsReimbursed(Integer isReimbursed) {
        this.isReimbursed = isReimbursed;
    }
}
