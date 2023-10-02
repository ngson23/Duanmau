package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase db;

    public SachDAO(Context context) {
        SQLiteOpenhelper dbHelper = new SQLiteOpenhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(String tenSach, int gia, int maloai, int soLuong) {
        ContentValues values = new ContentValues();
        values.put("TENSACH", tenSach);
        values.put("GIA", gia);
        values.put("MALOAI", maloai);
        values.put("SOLUONG_PH20371", soLuong);

        long row = db.insert("SACH", null, values);
        return row > 0;
    }

    public boolean update(int maSach,String tenSach, int gia, int maloai, int soLuong) {

        ContentValues values = new ContentValues();
        values.put("TENSACH", tenSach);
        values.put("GIA", gia);
        values.put("MALOAI", maloai);
        values.put("SOLUONG_PH20371", soLuong);

        long row = db.update("SACH", values, "MASACH=?", new String[]{String.valueOf(maSach)});
        return row > 0;
    }

    //1:Xóa thành công | 0: xóa thất bại | -1: không được xóa vì sách có trong phiếu mượn
    public int delete(int masach) {
        Cursor cursor = db.rawQuery("SELECT * FROM PHIEUMUON WHERE MASACH=?", new String[]{String.valueOf(masach)});
        if(cursor.getCount()!=0) {
            return -1;
        }

        int row = db.delete("SACH", "MASACH=?", new String[]{masach + ""});
        if(row == -1)
            return 0;
        return 1;
    }

    public List<Sach> selectAll() {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT S.MASACH, S.TENSACH, S.GIA, S.MALOAI, LS.TENLOAI, S.SOLUONG_PH20371 FROM SACH S, LOAISACH LS WHERE S.MALOAI = LS.MALOAI";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sach sach = new Sach();
            sach.setMaSach(cursor.getInt(0));
            sach.setTenSach(cursor.getString(1));
            sach.setGiaThue(cursor.getInt(2));
            sach.setMaLoai(cursor.getInt(3));
            sach.setTenLoai(cursor.getString(4));
            sach.setSoLuong(cursor.getInt(5));
            list.add(sach);
            cursor.moveToNext();

        }
        return list;
    }


}
