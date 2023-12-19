package app.constants;

/**
 * Description：流程节点类型常量
 * <p>Date: 2023/12/19
 * <p>Time: 13:59
 *
 * @Author SillyBaka
 **/
public enum ProcessNodeType {

    /**
     * 报销发起
     */
    APPLY(0),

    /**
     * 业务审批
     */
    APPROVAL(1),

    /**
     * 出纳付款
     */
    PAY(2),

    /**
     * 流程转发
     */
    RESEND(3),

    /**
     * 报销完成
     */
    FINISH(4);

    /**
     * 类型对应值
     */
    private int val;

    ProcessNodeType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
