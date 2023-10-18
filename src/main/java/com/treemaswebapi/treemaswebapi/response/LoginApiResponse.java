
package com.treemaswebapi.treemaswebapi.response;

import java.util.List;
import java.util.Map;

public class LoginApiResponse {
    private boolean success;
    private String message;
    private List<Map<String, String>> data; // to make the data easier to retrieved by the frontend
    private long totalData; // Add a field for total data count
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public long getTotalData() {
        return totalData;
    }

    public void setTotalData(long totalData) {
        this.totalData = totalData;
    }
    public LoginApiResponse(boolean success, String message, Object token, List<Map<String, String>> data,long totalData) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.totalData = totalData ;
    }

}   