package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database4 extends SQLiteOpenHelper {
    Context context;
    public Database4(Context context) {
        super(context, "myorder.db", null, 1);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists myorder (image blob, name text, discription text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists myorder");

    }

    public Boolean insertData(  byte[] image, String name, String discription )
    {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(" image", image);
        contentValues.put("name", name);
        contentValues.put("discription",discription);

        long result = MyDB.insert("myorder", null, contentValues);
        if(result == -1)return false;
        else
            return true;
    }

    public ArrayList<Db2ModelClass> getAlldata1()
    {
        try {
            ArrayList<Db2ModelClass>db2ModelClassArrayList = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if(sqLiteDatabase!=null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from myorder",null);
                if(cursor.getCount()!=0){
                    while(cursor.moveToNext()){
                        String name = cursor.getString(1);
                        String discription = cursor.getString(2);
                        byte[] image = cursor.getBlob(0);
                        Bitmap imag = BitmapFactory.decodeByteArray(image, 0,image.length);
                        db2ModelClassArrayList.add(
                                new Db2ModelClass(
                                        imag,name,discription
                                )
                        );

                    }
                    return db2ModelClassArrayList;

                }
                else {
                    Toast.makeText(context, "No data is ", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else {
                Toast.makeText(context, "Data is null", Toast.LENGTH_SHORT).show();
                return null;
            }

        }
        catch (Exception e){
            Toast.makeText(context, "getAllData:-"+e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}
