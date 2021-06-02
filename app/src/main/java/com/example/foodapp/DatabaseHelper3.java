package com.example.foodapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHelper3 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydbtwo.db";
    private static final String createTablequery = "create table imagedetailtwo (imagetitletwo TEXT" +",imagedestwo TEXT" +",imagetwo BLOB" + ",datetwo TEXT" + ",locationtwo TEXT" + ",quantitytwo TEXT)";
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageByte;
    public DatabaseHelper3(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTablequery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeImage(Model object){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToBitmap = object.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        imageByte = byteArrayOutputStream.toByteArray();
        ContentValues contentValues = new ContentValues();
        contentValues.put("imagetitletwo",object.getImagetitle());
        contentValues.put("imagedestwo",object.getImagedes());
        contentValues.put("imagetwo",imageByte);
        contentValues.put("locationtwo",object.getLocation());
        contentValues.put("datetwo",object.getDate());
        contentValues.put("quantitytwo", object.getQuantity());

        long result = db.insert("imagedetailtwo",null,contentValues);

        if(result != -1) db.close();

    }

    public ArrayList<Model> getAllImageData(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Model> modelList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from imagedetailtwo", null);
        if(cursor.getCount() != 0)
        {
            while(cursor.moveToNext()){
                String TITLE = cursor.getString(0);
                String DES = cursor.getString(1);
                byte[] imageBytes = cursor.getBlob(2);
                String DATE = cursor.getString(3);
                String LOCA = cursor.getString(4);
                String Quantity = cursor.getString(5);
                Bitmap objectbitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                modelList.add(new Model(TITLE,DES,objectbitmap,DATE,LOCA,Quantity));
            }
            return modelList;
        }
        else return modelList;
    }
}
