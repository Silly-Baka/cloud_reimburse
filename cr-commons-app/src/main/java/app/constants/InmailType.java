package app.constants;

/**
 * Description：站内信类型常量
 * <p>Date: 2023/12/28
 * <p>Time: 21:02
 *
 * @Author SillyBaka
 **/
public interface InmailType {

    /**
     * 流程流转
     */
    int PROCESS_CHANGE = 1;

    /**
     * 新待办事件
     */
    int NEW_EVENT = 2;

    /**
     * 流程督办
     */
    int PROCESS_SUPERVISE = 3;

    /**
     * 事件超时
     */
    int TIMEOUT_EVENT = 4;

    /**
     * 流程失败
     */
    int PROCESS_ABORT = 5;
}
