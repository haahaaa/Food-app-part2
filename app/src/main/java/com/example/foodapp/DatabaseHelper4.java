package com.example.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHelper4 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydbthree.db";
    private static final String createTablequery = "create table cartdetail (carttitle TEXT" +",cartquan TEXT" +",cartimage BLOB)";
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageByte;
    public DatabaseHelper4(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTablequery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeCart(Item object){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToBitmap = object.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        imageByte = byteArrayOutputStream.toByteArray();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cartimage",imageByte);
        contentValues.put("carttitle",object.getTitle());
        contentValues.put("cartquan", object.getQuantity());

        long result = db.insert("cartdetail",null,contentValues);

        if(result != -1) db.close();

    }

    public ArrayList<Item> getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Item> modelList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from cartdetail", null);
        if(cursor.getCount() != 0)
        {
            while(cursor.moveToNext()){
                String TITLE = cursor.getString(0);
                String Quantity = cursor.getString(1);
                byte[] imageBytes = cursor.getBlob(2);

                Bitmap objectbitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                modelList.add(new Item(TITLE,Quantity,objectbitmap));
            }
            return modelList;
        }
        else return modelList;
    }
}