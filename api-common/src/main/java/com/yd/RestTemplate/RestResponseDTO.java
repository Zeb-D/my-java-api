package com.yd.RestTemplate;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Yd on  2018-07-20
 * @description
 **/
@Data
@Accessors(chain = true)
public class RestResponseDTO<T> {
    private T data;
    private String message;
    private int statusCode;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
