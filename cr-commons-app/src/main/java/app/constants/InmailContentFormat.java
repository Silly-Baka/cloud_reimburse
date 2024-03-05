package app.constants;

/**
 * Description：站内信内容格式
 * <p>Date: 2023/12/28
 * <p>Time: 21:14
 *
 * @Author SillyBaka
 **/
public interface InmailContentFormat {

    /**
     * 流程流转-内容格式
     */
    String PROCESS_CHANGE_FORMAT = "您的报销单[%s]已到达新的流程节点[%s]，将由[%s]进行处理，可点击链接查看当前流程";

    /**
     * 新待办事件-内容格式
     */
    //TODO：待完善
    String NEW_EVENT_FORMAT = "您有新的待办事件[%s]！ \n 目前有%d个事件尚未处理，其中有%d个[被督办]，有%d个[已超时]";

    /**
     * 事件督办-内容格式
     */
    String PROCESS_SUPERVISE_FORMAT = "收到流程督办，目标报销单为[%s]，目前处于[%s]阶段，请及时处理此流程！";
}
