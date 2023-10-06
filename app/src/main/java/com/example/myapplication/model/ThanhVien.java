package com.example.myapplication.model;

public class ThanhVien {
    private int maTv;
    private String name;
    private String namSinh;
    private int gioitinh;

    public ThanhVien() {
    }

    public ThanhVien(int maTv, String name, String namSinh) {
        this.maTv = maTv;
        this.name = name;
        this.namSinh = namSinh;
    }

    public ThanhVien(int maTv, String name, String namSinh, int gioitinh) {
        this.maTv = maTv;
        this.name = name;
        this.namSinh = namSinh;
        this.gioitinh = gioitinh;
    }

    public int getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(int gioitinh) {
        this.gioitinh = gioitinh;
    }

    public int getMaTv() {
        return maTv;
    }

    public void setMaTv(int maTv) {
        this.maTv = maTv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
