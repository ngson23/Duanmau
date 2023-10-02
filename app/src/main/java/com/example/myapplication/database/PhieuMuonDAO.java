package com.example.myapplication.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase db;
    private  Context context;

    public PhieuMuonDAO(Context context) {
        this.context = context;
        SQLiteOpenhelper dbHelper = new SQLiteOpenhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(PhieuMuon pm) {
        ContentValues values = new ContentValues();
        values.put("MATT", pm.getMaTT());
        values.put("MATV", pm.getMaTV());
        values.put("MASACH", pm.getMaSach());
        values.put("TIENTHUE", pm.getTienThue());
        values.put("NGAY", pm.getNgay());
        values.put("TRASACH", pm.getTraSach());


        long row = db.insert("PHIEUMUON", null, values);
        return row > 0;
    }

    public boolean update(PhieuMuon pm) {

        ContentValues values = new ContentValues();
        values.put("MATT", pm.getMaTT());
        values.put("MATV", pm.getMaTV());
        values.put("MASACH", pm.getMaSach());
        values.put("TIENTHUE", pm.getTienThue());
        values.put("NGAY", pm.getNgay());
        values.put("TRASACH", pm.getTraSach());

        long row = db.update("PHIEUMUON", values, "MAPM=?", new String[]{String.valueOf(pm.getMaPM())});
        return row > 0;
    }

    public boolean delete(int mapm) {

        int row = db.delete("PHIEUMUON", "MAPM=?", new String[]{mapm + ""});
        return row > 0;
    }

    public List<PhieuMuon> selectAll() {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT PM.MAPM, PM.MATV, TV.NAME, PM.MATT,  PM.MASACH, SC.TENSACH, PM.NGAY, PM.TRASACH, PM.TIENTHUE FROM PHIEUMUON PM, THANHVIEN TV, THUTHU TT, SACH SC WHERE PM.MATV = TV.MATV AND PM.MATT = TT.MATT AND PM.MASACH = SC.MASACH ORDER BY PM.MAPM DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhieuMuon pm = new PhieuMuon();
            pm.setMaPM(cursor.getInt(0));
            pm.setMaTV(cursor.getInt(1));
            pm.setTenTv(cursor.getString(2));
            pm.setMaTT(cursor.getString(3));
            pm.setMaSach(cursor.getInt(4));
            pm.setTenSach(cursor.getString(5));
            pm.setNgay(cursor.getString(6));
            pm.setTraSach(cursor.getInt(7));
            pm.setTienThue(cursor.getInt(8));

            list.add(pm);
            cursor.moveToNext();

        }
        return list;
    }

    //top 10
    @SuppressLint("Range")
    public List<Top> getTop() {
        String sqlTop = "SELECT PM.MASACH, SC.TENSACH, COUNT(PM.MASACH) FROM PHIEUMUON PM, SACH SC WHERE PM.MASACH = SC.MASACH GROUP BY PM.MASACH, SC.TENSACH ORDER BY COUNT(PM.MASACH) DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);

        while (c.moveToNext()) {
            Top top = new Top();
            List<Sach> list1 = sachDAO.selectAll();


        }
        return  list;

    }

}
