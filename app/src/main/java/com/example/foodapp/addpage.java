package com.example.foodapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Intent.ACTION_GET_CONTENT;

public class addpage extends AppCompatActivity {

    private CalendarView calendarView;
    public TextView datetext;
    public EditText des,title,location;
    private Button savebtn;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 100;
    private static final int REQUEST_CODE_PERMISSION = 1;
    private Uri imageFilePath;
    private Bitmap bitmap;
    DatabaseHelper2 db;
    DatabaseHelper3 db2;
    String pickupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpage);

        des = findViewById(R.id.imagedes);
        title = findViewById(R.id.imagetitle);
        location = findViewById(R.id.location);
        imageView = findViewById(R.id.image);
        calendarView = findViewById(R.id.calendarView);
        datetext = findViewById(R.id.datetext);
        db = new DatabaseHelper2(this);
        db2 = new DatabaseHelper3(this);
        savebtn = findViewById(R.id.save);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            addpage.this,new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSION
                    );
                }else {
                    chooseImage(imageView);
                }
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                datetext.setText("Pick up date: " + date);
                pickupdate = date;
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_PERMISSION &&grantResults.length >0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                chooseImage(imageView);
            }else{
                Toast.makeText(this,"Permission denied!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!= null){
                imageFilePath = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
                imageView.setImageBitmap(bitmap);
            }
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void storeImage(View view){
        if(!title.getText().toString().isEmpty() && imageView.getDrawable() !=null && bitmap!= null && datetext != null) {
            db.storeImage(new Model(title.getText().toString(),des.getText().toString(),bitmap,pickupdate,location.getText().toString()));
            Toast.makeText(addpage.this,"Store informations Successfully",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(addpage.this, homepage.class);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(addpage.this,"Missing informations",Toast.LENGTH_SHORT).show();
        }
    }
    public void chooseImage(View objectView){

        try {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(i,PICK_IMAGE_REQUEST);


        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}