package com.isp.smarttrackapp.entities;

public class ResponseModelWithData<T> extends ResponseModel {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
