package app;

/**
 * Description：统一的返回值类型
 * <p>Date: 2023/12/1
 * <p>Time: 22:03
 *
 * @Author SillyBaka
 **/
public class CommonResult<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 请求是否成功
     */
    private Boolean isSuccess;

    public CommonResult() {
    }

    /**
     * 返回成功响应
     * @return
     * @param <T>
     */
    public static <T> CommonResult<T> ok() {
        return CommonResult.ok(null);
    }


    /**
     * 返回成功响应
     * @param data
     * @return
     * @param <T>
     */
    public static <T> CommonResult<T> ok(T data) {
        CommonResult<T> result = new CommonResult<>(data);
        result.setSuccess(true);
        return result;
    }

    /**
     * 返回错误响应
     * @param code
     * @param message
     * @return
     */
    public static CommonResult<?> fail(Integer code, String message) {
        CommonResult<Object> result = new CommonResult<>(code, message);
        result.setSuccess(false);
        return result;
    }


    public CommonResult(T data) {
        this.data = data;
        this.code = 200;
        this.message = "成功响应";
    }

    public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
