package com.yd.core;

/**
 * @author Yd on  2017-12-05
 * @Description：
 **/
public class ApiException extends RuntimeException{
    private String msg;

    public ApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ApiException(String message, String msg) {
        super(message);
        this.msg = msg;
    }

    public ApiException(String message, Throwable cause, String msg) {
        super(message, cause);
        this.msg = msg;
    }

    public ApiException(Throwable cause, String msg) {
        super(cause);
        this.msg = msg;
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
