package app.reimburse.dto;

/**
 * Description：
 * <p>Date: 2024/3/5
 * <p>Time: 0:25
 *
 * @Author SillyBaka
 **/
public class SuperviseSheetReqDTO {

    private Long sheetId;

    private String sheetName;

    private Long curNodeId;

    public Long getSheetId() {
        return sheetId;
    }

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Long getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(Long curNodeId) {
        this.curNodeId = curNodeId;
    }
}
