package app.reimburse.dto;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description：
 * <p>Date: 2024/2/26
 * <p>Time: 17:16
 *
 * @Author SillyBaka
 **/
public class ProcessChangeReqDTO {

    private Long processNodeId;
    private Long userId;
    private String feedBack;

    public Long getProcessNodeId() {
        return processNodeId;
    }

    public void setProcessNodeId(Long processNodeId) {
        this.processNodeId = processNodeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }
}
