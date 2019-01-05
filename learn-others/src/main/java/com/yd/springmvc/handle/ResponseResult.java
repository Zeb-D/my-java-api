package com.yd.springmvc.handle;

/**
 * 异常处理返回结果，只有Controller层异常时就返回此实体
 *
 * @author Yd on 2017-11-08
 */
public class ResponseResult {

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer ERROR_CODE = -99999;
    public static final String ERROR_DESCRIPTION = "error";
    private static final String SUCCESS_DESCRIPTION = "success";
    /**
     * 交互结果
     */
    private boolean success;

    /**
     * 返回状态标识
     */
    private Integer status;
    /**
     * 返回信息描述
     */
    private String msg;
    /**
     * 返回数据对象
     */
    private Object data;

    public ResponseResult() {
        this(false);
    }

    public ResponseResult(boolean success) {
        this(success, SUCCESS_DESCRIPTION, SUCCESS_CODE);
    }

    public ResponseResult(boolean success, String msg) {
        this(success, msg, SUCCESS_CODE);
    }

    public ResponseResult(boolean success, String msg, Integer status) {
        this.success = success;
        this.msg = msg;
        this.status = status;
    }

    public ResponseResult(boolean success, String msg, Integer status, Object data) {
        this.success = success;
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public static ResponseResult error() {
        return new ResponseResult(false, ERROR_DESCRIPTION, ERROR_CODE);
    }

    public static ResponseResult error(Integer status, String msg) {
        return new ResponseResult(false, msg, status);
    }

    public static ResponseResult error(Object data, Integer status, String msg) {
        return new ResponseResult(false, msg, status, data);
    }

    public static ResponseResult error(Object data) {
        return new ResponseResult(false, ERROR_DESCRIPTION, ERROR_CODE, data);
    }

    public static ResponseResult success() {
        return new ResponseResult(true, SUCCESS_DESCRIPTION, SUCCESS_CODE);
    }

    public static ResponseResult success(String msg) {
        return new ResponseResult(true, msg);
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(true, SUCCESS_DESCRIPTION, SUCCESS_CODE, data);
    }

    public static ResponseResult success(String msg, Object data) {
        return new ResponseResult(true, msg, SUCCESS_CODE, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "success=" + success +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
