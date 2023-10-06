package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.SQLiteOpenhelper;

public class demoDB {
    private SQLiteDatabase db;
    public  demoDB(Context context) {
        SQLiteOpenhelper dbHelper = new SQLiteOpenhelper(context);
        db = dbHelper.getWritableDatabase();

    }
}
