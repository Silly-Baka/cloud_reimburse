package app.reimburse.dto;

import app.reimburse.entity.InvoiceInfo;

import java.util.Date;
import java.util.List;

/**
 * Description：发票-添加用DTO
 * <p>Date: 2023/12/18
 * <p>Time: 18:47
 *
 * @Author SillyBaka
 **/
public class InvoiceAddDTO {

    /**
     * 发票持有者id
     */
    private Long ownerId;

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
     * 开票日期
     */
    private Date invDate;

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
     * 发票信息列表
     */
    private List<InvoiceInfo> invoiceInfoList;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
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

    public List<InvoiceInfo> getInvoiceInfoList() {
        return invoiceInfoList;
    }

    public void setInvoiceInfoList(List<InvoiceInfo> invoiceInfoList) {
        this.invoiceInfoList = invoiceInfoList;
    }
}
