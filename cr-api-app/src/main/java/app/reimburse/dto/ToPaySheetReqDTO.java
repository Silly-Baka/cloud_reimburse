package app.reimburse.dto;

import java.util.Date;

/**
 * Description：
 * <p>Date: 2024/2/26
 * <p>Time: 20:29
 *
 * @Author SillyBaka
 **/
public class ToPaySheetReqDTO {

    private Long id;

    private String name;

    private Integer type;

    private Date createTime;

    private String applicantName;

    private Double price;

    private Integer payState;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }
}
