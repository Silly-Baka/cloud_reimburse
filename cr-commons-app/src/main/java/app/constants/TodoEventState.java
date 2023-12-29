package app.constants;

/**
 * Description：待办事件状态常量
 * <p>Date: 2023/12/29
 * <p>Time: 22:19
 *
 * @Author SillyBaka
 **/
public interface TodoEventState {
    /**
     * 未处理
     */
    int UN_HANDLED = 0;

    /**
     * 已处理
     */
    int HANDLED = 1;

    /**
     * 被督办
     */
    int SUPERVISED = 2;

    /**
     * 已超时
     */
    int OUT_OF_TIME = 3;
}
