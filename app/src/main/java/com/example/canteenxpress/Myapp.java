package com.example.canteenxpress;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.canteenxpress.database.DatabaseHelper;

public class Myapp extends Application {
    public static DatabaseHelper dbHelper;
    public static SQLiteDatabase db;
    public static Myapp myapp;

    @Override
    public void onCreate() {
        super.onCreate();
        myapp = this;

        dbHelper = new DatabaseHelper(myapp);
        db = dbHelper.getWritableDatabase();
    }
}
