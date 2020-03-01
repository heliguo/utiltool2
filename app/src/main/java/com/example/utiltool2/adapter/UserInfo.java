package com.example.utiltool2.adapter;

public class UserInfo {

    private String account;
    private String password;


    public UserInfo(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

}
