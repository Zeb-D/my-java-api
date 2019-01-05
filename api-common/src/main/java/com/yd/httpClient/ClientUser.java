package com.yd.httpClient;

import com.yd.common.util.Maper;

import java.util.Map;

public class ClientUser {
    private String username;
    private String password;
    private boolean rememberMe;

    public ClientUser(String username, String password, boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public Map<String, String> reserveMap() {
        return Maper.of("username", this.username, "password", this.password, "rememberMe", Boolean.toString(this.rememberMe));
    }
}