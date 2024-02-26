package app.reimburse.dto;

import java.util.List;

/**
 * Description：
 * <p>Date: 2024/2/26
 * <p>Time: 20:11
 *
 * @Author SillyBaka
 **/
public class FinancePayReqDTO {

    /**
     * 报销单id列表
     */
    private List<Long> reimburseSheetIdList;

    /**
     * 操作者id
     */
    private Long todoUser;

    public List<Long> getReimburseSheetIdList() {
        return reimburseSheetIdList;
    }

    public void setReimburseSheetIdList(List<Long> reimburseSheetIdList) {
        this.reimburseSheetIdList = reimburseSheetIdList;
    }

    public Long getTodoUser() {
        return todoUser;
    }

    public void setTodoUser(Long todoUser) {
        this.todoUser = todoUser;
    }
}
