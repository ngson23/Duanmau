package com.example.myapplication.model;

public class ThuThu {
    private String maTT;
    private String name;
    private String matKhau;
    private String loaiTk;

    public ThuThu() {
    }

    public ThuThu(String maTT, String name, String matKhau) {
        this.maTT = maTT;
        this.name = name;
        this.matKhau = matKhau;
    }

    public String getLoaiTk() {
        return loaiTk;
    }

    public void setLoaiTk(String loaiTk) {
        this.loaiTk = loaiTk;
    }

    public ThuThu(String maTT, String name, String matKhau, String loaiTk) {
        this.maTT = maTT;
        this.name = name;
        this.matKhau = matKhau;
        this.loaiTk = loaiTk;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
