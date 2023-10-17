package com.treemaswebapi.treemaswebapi.request;

public class AbsenImageRequest {
    private String nik;
    private byte[] image;

    // Constructors, getters, and setters

    public AbsenImageRequest() {
        // Default constructor
    }

    public AbsenImageRequest(String nik, byte[] image) {
        this.nik = nik;
        this.image = image;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
