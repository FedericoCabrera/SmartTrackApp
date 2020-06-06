package com.isp.smarttrackapp.entities;

public class ResponseModelWithData<T> extends ResponseModel {
    private T data;

    public ResponseModelWithData() {
    }

    public ResponseModelWithData(boolean isResponseOk, String errorMessage) {
        super(isResponseOk, errorMessage);
    }

    public ResponseModelWithData(boolean isResponseOk, String errorMessage, T data) {
        super(isResponseOk, errorMessage);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
