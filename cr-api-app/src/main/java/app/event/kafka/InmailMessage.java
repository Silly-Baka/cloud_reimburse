package app.event.kafka;

import app.reimburse.entity.ProcessNode;
import app.reimburse.entity.ReimburseSheet;

/**
 * Description：站内信topic中的信息
 * <p>Date: 2023/12/28
 * <p>Time: 0:20
 *
 * @Author SillyBaka
 **/
public class InmailMessage {

    /**
     * 消息唯一id
     */
    private Long id;

    /**
     * 站内信类型， 1-通知型
     *            2-待办事件型
     */
    private Integer inmailType;

    /**
     * 报销单信息
     */
    private ReimburseSheet reimburseSheet;

    /**
     * 目前所处的报销流程节点
     */
    private ProcessNode processNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInmailType() {
        return inmailType;
    }

    public void setInmailType(Integer inmailType) {
        this.inmailType = inmailType;
    }

    public ReimburseSheet getReimburseSheet() {
        return reimburseSheet;
    }

    public void setReimburseSheet(ReimburseSheet reimburseSheet) {
        this.reimburseSheet = reimburseSheet;
    }

    public ProcessNode getProcessNode() {
        return processNode;
    }

    public void setProcessNode(ProcessNode processNode) {
        this.processNode = processNode;
    }
}
