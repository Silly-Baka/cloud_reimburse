package app.event.dto;

/**
 * Description：
 * <p>Date: 2024/2/26
 * <p>Time: 17:22
 *
 * @Author SillyBaka
 **/
public class DoneEventReqDTO {
    private Long sheetId;
    private Long todoUser;

    public Long getSheetId() {
        return sheetId;
    }

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }

    public Long getTodoUser() {
        return todoUser;
    }

    public void setTodoUser(Long todoUser) {
        this.todoUser = todoUser;
    }
}
