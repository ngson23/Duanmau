package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        SQLiteOpenhelper dbHelper = new SQLiteOpenhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(LoaiSach ls) {
        ContentValues values = new ContentValues();
        values.put("TENLOAI", ls.getTenLoai());

        long row = db.insert("LOAISACH", null, values);
        return row > 0;
    }

    public boolean update(LoaiSach ls) {

        ContentValues values = new ContentValues();
        values.put("TENLOAI", ls.getTenLoai());

        long row = db.update("LOAISACH", values, "MALOAI=?", new String[]{String.valueOf(ls.getMaLoai())});
        return row > 0;
    }

    public int delete(int maloai) {
        Cursor cursor = db.rawQuery("SELECT * FROM SACH WHERE MALOAI=?", new String[]{maloai+""});
        if(cursor.getCount()!=0) {
            return -1;
        }

        int row = db.delete("LOAISACH", "MALOAI=?", new String[]{maloai + ""});
        if(row == -1) {
            return 0;
        } else {
            return 1;
        }

    }

    public List<LoaiSach> selectAll() {
        List<LoaiSach> list = new ArrayList<>();
        String sql = "SELECT * FROM LOAISACH";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiSach ls = new LoaiSach();
            ls.setMaLoai(cursor.getInt(0));
            ls.setTenLoai(cursor.getString(1));
            list.add(ls);
            cursor.moveToNext();

        }
        return list;
    }
}
