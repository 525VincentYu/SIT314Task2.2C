package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class Database extends SQLiteOpenHelper{

    Context context;
    public Database(Context context) {
        super(context, "light_sensor.db", null, 1);
        this.context =context;}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists light_sensor(ID integer primary key autoincrement,light_value float);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists light_sensor");


    }

    public Boolean insertData(float light_value){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("light_value",light_value);

        long result = DB.insert("light_sensor",null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }


}