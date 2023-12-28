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
    String PROCESS_CHANGE_FORMAT = "您的报销单[%s]已到达新的流程节点[%s]，将由[%s]进行处理，可点击链接查看流程图";

    /**
     * 新待办事件-内容格式
     */
    //TODO：待完善
    String NEW_EVENT_FORMAT = "您有新的待办事件，目前有%d个事件尚未处理，可点击链接跳转至此事件";
}
