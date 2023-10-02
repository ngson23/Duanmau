package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteOpenhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "LIB";

    public SQLiteOpenhelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    //tạo bảng
    static final String TB_THANHVIEN = "CREATE TABLE THANHVIEN(\n" +
            "  MATV INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  NAME TEXT,\n" +
            "  NAMSINH TEXT,\n" +
            " GIOITINH_PH20371 INTEGER\n );";

    static final String TB_THUTHU = "CREATE TABLE THUTHU(\n" +
            "  MATT TEXT PRIMARY KEY,\n" +
            "  NAME TEXT,\n" +
            "  MATKHAU TEXT,\n" +
            " LOAITK TEXT );";
    static final String TB_LOAISACH = "CREATE TABLE LOAISACH(\n" +
            "  MALOAI INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  TENLOAI TEXT\n" +
            "\n" +
            "  );";
    static final String TB_SACH = "CREATE TABLE SACH(\n" +
            "              MASACH INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            "             TENSACH TEXT,\n" +
            "  GIA INTEGER,\n" +
            "  MALOAI INTEGER REFERENCES LOAISACH(MALOAI),\n" +
            "  SOLUONG_PH20371 INTEGER      );";
    static final String TB_PHIEUMUON = "CREATE TABLE PHIEUMUON(\n" +
            "  MAPM INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  MATT TEXT REFERENCES THUTHU(MATT),\n" +
            "  MATV INTEGER REFERENCES THANHVIEN(MATV),\n" +
            "  MASACH INTEGER REFERENCES SACH(MASACH),\n" +
            "  NGAY TEXT  NOT NULL,\n" +
            "  TRASACH INTEGER,\n" +
            "  TIENTHUE INTEGER);\n" +
            "  ";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TB_THANHVIEN);
        db.execSQL(TB_THUTHU);
        db.execSQL(TB_LOAISACH);
        db.execSQL(TB_SACH);
        db.execSQL(TB_PHIEUMUON);

        db.execSQL("INSERT INTO THUTHU VALUES('thuthu1', 'Nguyễn Văn A', '123', 'Admin'),('thuthu2', 'Nguyễn văn b', '456', 'ThuThu')");
        db.execSQL("INSERT INTO LOAISACH VALUES(1, 'THIẾU NHI'), (2, 'TÌNH CẢM'), (3, 'GIÁO KHOA')");
        db.execSQL("INSERT INTO SACH VALUES(1, 'HÃY ĐỢI ĐẤY', 2500, 1, 10), (2, 'THẰNG CUỘI', 1500, 2, 13), (3, 'LẬP TRÌNH ANDROID', 2000, 3, 6)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TB_THANHVIEN");
        db.execSQL("DROP TABLE IF EXISTS TB_THUTHU");
        db.execSQL("DROP TABLE IF EXISTS TB_LOAISACH");
        db.execSQL("DROP TABLE IF EXISTS TB_SACH");
        db.execSQL("DROP TABLE IF EXISTS TB_PHIEUMUON");
        onCreate(db);
    }
}
