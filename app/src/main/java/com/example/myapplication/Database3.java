package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Database3 extends SQLiteOpenHelper {
    Context context;
    public Database3(Context context) {
        super(context, "order.db", null, 1);
        this.context =context;
    }
//order database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists orders (ID integer primary key autoincrement, image blob, sender text, pickupime text, reciever text,  weight text, " +
                "type text, width text, height text, length text,pickuplocation text, dropofflocation text, l1 text, l2 text , l3 text, l4 text, distance text );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists orders");

    }
    //insert order information
    public Boolean insertData(  byte[] image, String sender, String pickupime, String reciever, String weight, String type, String width, String height, String length, String pickuplocation, String dropofflocation, Double l1, Double l2, Double l3, Double l4, Double distance )
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image",image);
        contentValues.put("sender",sender);
        contentValues.put("pickupime",pickupime);
        contentValues.put("reciever",reciever);
        contentValues.put("weight",weight);
        contentValues.put("type",type);
        contentValues.put("width",width);
        contentValues.put("height",height);
        contentValues.put("length",length);
        contentValues.put("pickuplocation",pickuplocation);
        contentValues.put("dropofflocation",dropofflocation);
        contentValues.put("l1",l1);
        contentValues.put("l2",l2);
        contentValues.put("l3",l3);
        contentValues.put("l4",l4);
        contentValues.put("distance",distance);

        long result = DB.insert("orders",null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }



    }

//get specific data

    public String getsender(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(2);
    }

    public Bitmap getimage (int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        byte[] bitmap = cursor.getBlob(1);
        Bitmap image = BitmapFactory.decodeByteArray(bitmap,0,bitmap.length);
        return image;
    }
    public String getpicktime(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(3);
    }
    public String getreciver(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(4);
    }
    public String getweight(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(5);
    }
    public String gettype(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(6);
    }
    public String getwidth(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(7);
    }
    public String getheight(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(8);
    }
    public String getlength(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(9);
    }
    public String getpickuplocation(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(10);
    }
    public String getdropofflocation(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(11);
    }

    public String getdistance(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(16);
    }
    public String getl1(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(12);
    }
    public String getl2(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(13);
    }
    public String getl3(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(14);
    }
    public String getl4(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from orders where ID = ID",null);
        cursor.moveToFirst();
        return cursor.getString(15);
    }

    public ArrayList<LatLng> getAlldata1()
    {
        try {

            ArrayList<LatLng>ArrayList = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if(sqLiteDatabase!=null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from orders",null);
                if(cursor.getCount()!=0){
                    while(cursor.moveToNext()){
                        double l1 = cursor.getDouble(12);
                        double l2 = cursor.getDouble(13);
                        ArrayList.add(new LatLng(l1,l2)

                        );

                    }
                    return ArrayList;

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
    public ArrayList<LatLng> getAlldata2()
    {
        try {

            ArrayList<LatLng>ArrayList = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if(sqLiteDatabase!=null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from orders",null);
                if(cursor.getCount()!=0){
                    while(cursor.moveToNext()){
                        double l1 = cursor.getDouble(14);
                        double l2 = cursor.getDouble(15);
                        ArrayList.add(new LatLng(l1,l2)

                        );

                    }
                    return ArrayList;

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





    public ArrayList<Db2ModelClass> getAlldata()
    {
        try {
            ArrayList<Db2ModelClass>db2ModelClassArrayList = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if(sqLiteDatabase!=null){
            Cursor cursor = sqLiteDatabase.rawQuery("select * from truck",null);
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
