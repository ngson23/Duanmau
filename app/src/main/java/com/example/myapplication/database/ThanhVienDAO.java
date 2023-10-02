package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context) {
        SQLiteOpenhelper dbHelper = new SQLiteOpenhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(ThanhVien tv) {
        ContentValues values = new ContentValues();
        values.put("NAME", tv.getName());
        values.put("NAMSINH", tv.getNamSinh());
        values.put("GIOITINH_PH20371", tv.getGioitinh());

        long row = db.insert("THANHVIEN", null, values);
        return row>0;
    }

    public  boolean update(ThanhVien tv) {

        ContentValues values = new ContentValues();
        values.put("NAME", tv.getName());
        values.put("NAMSINH", tv.getNamSinh());
        values.put("GIOITINH_PH20371", tv.getGioitinh());

        long row = db.update("THANHVIEN", values, "MATV=?", new String[]{String.valueOf(tv.getMaTv())});
        return row>0;
    }

    public  boolean delete(int matv) {

        int row = db.delete("THANHVIEN", "MATV=?", new String[]{matv+""});
        return row>0;
    }

    public List<ThanhVien> selectAll() {
        List<ThanhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM THANHVIEN";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThanhVien tv = new ThanhVien();
            tv.setMaTv(cursor.getInt(0));
            tv.setName(cursor.getString(1));
            tv.setNamSinh(cursor.getString(2));
            tv.setGioitinh(cursor.getInt(3));
            list.add(tv);
            cursor.moveToNext();

        }
        return list;
    }


}
