package com.ei.desktop.domain;

/**
 * @author yitiansong
 * 2024/8/4
 */
@SuppressWarnings("unused")
public class Account {
    private String username;
    private String password;
    private String token;
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
