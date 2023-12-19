package app.constants;

/**
 * Description：通用状态字段
 * <p>Date: 2023/12/19
 * <p>Time: 14:08
 *
 * @Author SillyBaka
 **/
public enum CommonState {

    /**
     * 失败
     */
    FAIL(-1),

    /**
     * 正在使用/执行
     */
    CONTINUE(0),

    /**
     * 已完成
     */
    PASS(1);

    private final int val;

    CommonState(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
