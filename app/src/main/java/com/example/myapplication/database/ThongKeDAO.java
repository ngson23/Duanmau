package com.example.myapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    SQLiteOpenhelper dbHelper;

    public ThongKeDAO(Context context) {
       dbHelper = new SQLiteOpenhelper(context);
    }

    public List<Sach> getTop10() {
        List<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PM.MASACH, SC.TENSACH, COUNT(PM.MASACH) FROM PHIEUMUON PM, SACH SC WHERE PM.MASACH = SC.MASACH GROUP BY PM.MASACH, SC.TENSACH ORDER BY COUNT(PM.MASACH) DESC LIMIT 10", null);

        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int getDoanhThu(String ngayBatDau, String ngayKetThuc) {
        ngayBatDau = ngayBatDau.replace("/", "");
        ngayKetThuc = ngayKetThuc.replace("/", "");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(TIENTHUE) FROM PHIEUMUON WHERE SUBSTR(NGAY,7) || SUBSTR(NGAY,4,2) || SUBSTR(NGAY,1,2) BETWEEN ? AND ? ", new String[]{ngayBatDau, ngayKetThuc});
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            return cursor.getInt(0);

        }
        return 0;
    }
}
