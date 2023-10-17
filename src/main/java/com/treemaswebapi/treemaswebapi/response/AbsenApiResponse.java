package com.treemaswebapi.treemaswebapi.response;

public class AbsenApiResponse {
    private boolean isTokenOk;
    private String message;
    
    public boolean isTokenOk() {
        return isTokenOk;
    }

    public void setTokenOk(boolean isTokenOk) {
        this.isTokenOk = isTokenOk;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public AbsenApiResponse(boolean isTokenOk, String message, Object object, int i) {
        this.isTokenOk = isTokenOk;
        this.message = message;
    }
}
