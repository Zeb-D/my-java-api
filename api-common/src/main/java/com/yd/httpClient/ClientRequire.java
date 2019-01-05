package com.yd.httpClient;

public class ClientRequire {
    public ClientRequire() {
    }

    public boolean relogin(ClientResult result) {
        return result.getStatus().getCode() == 401;
    }
}