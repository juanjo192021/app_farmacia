package com.app.farmacia_fameza.models.response;

import com.app.farmacia_fameza.models.Data;

public class ApiResponse {
    private int statusCode;
    private String message;
    private boolean success;
    private Data data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ApiResponse(){}

    public ApiResponse(int statusCode, String message, boolean success, Data data) {
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
        this.data = data;
    }
}
