package com.isp.smarttrackapp.entities;

public class ResponseModel{
    private boolean isResponseOK;
    private String errorMessage;

    public ResponseModel() {
    }

    public ResponseModel(boolean isResponseOk, String errorMessage) {
        this.isResponseOK = isResponseOk;
        this.errorMessage = errorMessage;
    }

    public boolean isResponseOK() {
        return isResponseOK;
    }

    public void setResponseOK(boolean responseOk) {
        isResponseOK = responseOk;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
