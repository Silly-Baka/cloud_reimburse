package app.event.kafka;

import app.common.LeafTag;
import app.reimburse.entity.ProcessNode;
import app.reimburse.entity.ReimburseSheet;

/**
 * Description：待办事件topic中的信息
 * <p>Date: 2023/12/28
 * <p>Time: 0:21
 *
 * @Author SillyBaka
 **/
@LeafTag(tag = "eventMessage")
public class EventMessage {

    /**
     * 消息唯一id
     */
    private Long id;

    /**
     * 事件目标报销单信息
     */
    private ReimburseSheet reimburseSheet;

    /**
     * 事件中需要处理的流程节点信息
     */
    private ProcessNode processNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
