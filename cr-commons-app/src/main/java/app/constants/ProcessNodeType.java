package app.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：流程节点类型常量
 * <p>Date: 2023/12/19
 * <p>Time: 13:59
 *
 * @Author SillyBaka
 **/
public class ProcessNodeType {

    /**
     * 报销发起
     */
    public static final int APPLY = 0;

    /**
     * 业务审批
     */
    public static final int APPROVAL = 1;

    /**
     * 出纳付款
     */
    public static final int PAY = 2;

    /**
     * 流程转发
     */
    public static final int RESEND = 3;

    /**
     * 报销完成
     */
    public static final int FINISH = 4;

    /**
     * [常量值 --> 类型名]的映射
     */
    public static final Map<Integer, String> NODE_TYPE_NAMES = new HashMap<>();

    static {
        NODE_TYPE_NAMES.put(APPLY, "报销发起");
        NODE_TYPE_NAMES.put(APPROVAL, "业务审批");
        NODE_TYPE_NAMES.put(PAY, "出纳付款");
        NODE_TYPE_NAMES.put(RESEND, "流程转发");
        NODE_TYPE_NAMES.put(FINISH, "报销完成");
    }
}
