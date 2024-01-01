package app.leaf.entity;

import java.sql.Timestamp;

/**
 * Description：
 * <p>Date: 2024/1/2
 * <p>Time: 1:43
 *
 * @Author SillyBaka
 **/
public class LeafAlloc {

    private String bizTag;

    private Long maxId;

    private Integer step;

    private String description;

    private Timestamp updateTime;

    public String getBizTag() {
        return bizTag;
    }

    public void setBizTag(String bizTag) {
        this.bizTag = bizTag;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
