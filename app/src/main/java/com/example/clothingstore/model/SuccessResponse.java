package com.example.clothingstore.model;

public class SuccessResponse <T> {
    private String status;
    private String massage;
    private T data;

    public SuccessResponse(String status, String massage, T data) {
        this.status = status;
        this.massage = massage;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
