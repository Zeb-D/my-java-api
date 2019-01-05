package com.yd.httpClient;

public class ClientResult {
    private HttpStatus status;
    private String result;

    public ClientResult(HttpStatus status, String result) {
        this.status = status;
        this.result = result;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getResult() {
        return this.result;
    }

    @Override
    public String toString() {
        return "ClientResult{status=" + this.status.getCode() + ", result='" + this.result + '\'' + '}';
    }
}