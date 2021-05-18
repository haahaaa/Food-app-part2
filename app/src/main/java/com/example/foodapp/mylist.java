package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class mylist extends AppCompatActivity {
    private Button add;
    private DatabaseHelper3 db;
    private RecyclerView recyclerView;
    private RVadapter rVadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);
        add = findViewById(R.id.addbtn1);
        db = new DatabaseHelper3(this);
        recyclerView = findViewById(R.id.recycleview2);
        rVadapter = new RVadapter(db.getAllImageData());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rVadapter);




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mylist.this, addpage.class);
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
        if(id == R.id.mylist) closeOptionsMenu();
        else if(id == R.id.homedirec)
        {
            Intent i = new Intent(mylist.this, homepage.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}