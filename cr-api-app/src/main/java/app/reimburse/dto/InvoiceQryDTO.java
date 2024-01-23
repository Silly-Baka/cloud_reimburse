package app.reimburse.dto;

/**
 * Description：发票查询用DTO
 * <p>Date: 2024/1/23
 * <p>Time: 22:46
 *
 * @Author SillyBaka
 **/
public class InvoiceQryDTO {

    /**
     * 发票id
     */
    private Long id;

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
     * 是否已报销，0-否，1-报销中，2-已报销
     */
    private Integer isReimbursed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getIsReimbursed() {
        return isReimbursed;
    }

    public void setIsReimbursed(Integer isReimbursed) {
        this.isReimbursed = isReimbursed;
    }
}
