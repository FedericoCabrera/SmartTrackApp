package com.isp.smarttrackapp.entities;

public class ResponseModel<T> {
    private boolean isResponseOK;
    private String errorMessage;
    private T data;

    public ResponseModel() {
    }

    public ResponseModel(boolean isResponseOk, String errorMessage) {
        this.isResponseOK = isResponseOk;
        this.errorMessage = errorMessage;
    }

    public ResponseModel(boolean isResponseOk, String errorMessage, T data) {
        this.isResponseOK = isResponseOk;
        this.errorMessage = errorMessage;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
