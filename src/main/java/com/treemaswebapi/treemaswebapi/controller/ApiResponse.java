
package com.treemaswebapi.treemaswebapi.controller;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object token;
    private String namaKaryawan;
    private String nik;
    private String deviceId;

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public ApiResponse(boolean success, String message, Object token, String namaKaryawan, String deviceId, String nik) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.namaKaryawan = namaKaryawan;
        this.deviceId = deviceId;
        this.nik = nik;

    }
}   