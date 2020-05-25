package com.isp.smarttrackapp.model.entities;

public class Employee {

    private String name;
    private String lastName;
    private String id;
    private String userName;
    private String password;
    private String identyNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getIdentyNumber() {
        return identyNumber;
    }

    public void setIdentyNumber(String identyNumber) {
        this.identyNumber = identyNumber;
    }
}
