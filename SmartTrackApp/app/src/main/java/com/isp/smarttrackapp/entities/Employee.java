package com.isp.smarttrackapp.entities;

import androidx.annotation.NonNull;

public class Employee {

    private String name;
    private String lastName;
    private String id;
    private String userName;
    private String password;
    private String identityNumber;
    private String status;
    //public Status enumStatus;
    public enum Status { DISCONNECTED, CONNECTED, ON_A_TRIP }

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

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getStatus() {
        return this.status;
    }
    public String showStatus() {
        String st = "";
        if(this.status.equals(Status.DISCONNECTED)) { st = "Desconectado"; }
        if(this.status.equals(Status.CONNECTED)) { st = "Conectado"; }
        if(this.status.equals(Status.ON_A_TRIP)) { st = "En Viaje"; }
        return st;
    }
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name.concat(" ").concat(this.lastName).concat(" - ").concat(this.showStatus());
    }
}
