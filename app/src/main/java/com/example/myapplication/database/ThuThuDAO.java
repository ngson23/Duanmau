package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.ThanhVien;
import com.example.myapplication.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;
    SharedPreferences sharedPreferences;

    public ThuThuDAO(Context context) {
        SQLiteOpenhelper dbHelper = new SQLiteOpenhelper(context);
        db = dbHelper.getWritableDatabase();
        sharedPreferences = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
    }

    //đăng nhập
    public boolean checkLogin(String matt, String pass) {
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE MATT=? AND MATKHAU=?", new String[]{matt, pass});
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
             SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.putString("matt", cursor.getString(0));
             editor.putString("loaitk", cursor.getString(3));
             editor.putString("username", cursor.getString(1));

             editor.commit();

            return true;
        } else {
            return false;
        }
    }



    public boolean insert(ThuThu tt) {
        ContentValues values = new ContentValues();
        values.put("MATT", tt.getMaTT());
        values.put("NAME", tt.getName());
        values.put("MATKHAU", tt.getMatKhau());
        values.put("LOAITK", tt.getLoaiTk());

        long row = db.insert("THUTHU", null, values);
        return row>0;
    }

    public  boolean update(ThuThu tt) {

        ContentValues values = new ContentValues();

        values.put("NAME", tt.getName());
        values.put("MATKHAU", tt.getMatKhau());

        long row = db.update("THUTHU", values, "MATT=?", new String[]{String.valueOf(tt.getMaTT())});
        return row>0;
    }

    public  boolean delete(int matt) {

        int row = db.delete("THUTHU", "MATT=?", new String[]{matt+""});
        return row>0;
    }

    public List<ThuThu> selectAll() {
        List<ThuThu> list = new ArrayList<>();
        String sql = "SELECT * FROM THUTHU";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThuThu tt = new ThuThu();
            tt.setMaTT(cursor.getString(0));
            tt.setName(cursor.getString(1));
            tt.setMatKhau(cursor.getString(2));
            tt.setLoaiTk(cursor.getString(3));
            list.add(tt);
            cursor.moveToNext();

        }
        return list;
    }

    public boolean updatePass(String user, String oldPass, String newPass) {
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE MATT=? AND MATKHAU=?", new String[]{user, oldPass});
        if(cursor.getCount()>0) {
            ContentValues values = new ContentValues();
            values.put("MATKHAU", newPass);
            long check = db.update("THUTHU",values, "MATT=?", new String[]{user});
            if(check == -1) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
