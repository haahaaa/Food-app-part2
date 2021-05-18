package com.example.foodapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class homepage extends AppCompatActivity {
    private Button addbtn;
    private DatabaseHelper2 db;
    private DatabaseHelper3 db2;
    private RecyclerView recyclerView;
    private RVadapter rVadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        addbtn = findViewById(R.id.addbtn);
        db = new DatabaseHelper2(this);
        db2 = new DatabaseHelper3(this);
        recyclerView = findViewById(R.id.recycleview);
        rVadapter = new RVadapter(db.getAllImageData());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rVadapter);

        rVadapter.setOnItemClickListener(new RVadapter.OnItemClickListener() {
            @Override
            public void onShareClick(int position) {
                String TITLE = db.getAllImageData().get(position).imagetitle;
                String DES = db.getAllImageData().get(position).imagedes;
                String LOCA = db.getAllImageData().get(position).location;
                String DATE = db.getAllImageData().get(position).date;
                Bitmap bitmap = db.getAllImageData().get(position).getImage();


                if(db2.getAllImageData().size() == 0){
                    db2.storeImage(new Model(TITLE,DES,bitmap,DATE,LOCA));
                }
                else if(db2.getAllImageData().get(position).imagetitle.equals(TITLE)
                        | db2.getAllImageData().get(position).imagedes.equals(DES)
                        | db2.getAllImageData().get(position).image.equals(bitmap)
                        |db2.getAllImageData().get(position).location.equals(LOCA)
                        |db2.getAllImageData().get(position).date.equals(DATE))
                    Toast.makeText(homepage.this,"You shared before, won't add to the user list.",Toast.LENGTH_SHORT).show();
                else{
                    db2.storeImage(new Model(TITLE,DES,bitmap,DATE,LOCA));
                }

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "TITLE: " + TITLE +"\n" + "DESCRIPTION: " + DES + "\n" + "PICK UP DATE: " + DATE + "\n" + "LOCATION: " + LOCA);
                startActivity(i.createChooser(i,"share"));
            }
        });


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepage.this,addpage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_tool,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.homedirec) closeOptionsMenu();
        else if(id == R.id.mylist)
        {
            Intent i = new Intent(homepage.this, mylist.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}