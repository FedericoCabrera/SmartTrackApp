package com.isp.smarttrackapp.entities;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
