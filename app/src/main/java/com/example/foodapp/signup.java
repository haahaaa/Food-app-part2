package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    private Button saveBtn;
    private EditText username, password, compassword;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.UN);
        password = findViewById(R.id.PW);
        compassword = findViewById(R.id.CPW);
        saveBtn = findViewById(R.id.saveBtn);
        db = new DatabaseHelper(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pw = password.getText().toString().trim();
                String cpw = compassword.getText().toString().trim();

                if(pw.equals(cpw)){
                    long val = db.addUser(user,pw);
                    if(val>0){
                        Toast.makeText(signup.this, "You have registered!",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(signup.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(signup.this, "Registeration errors!",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(signup.this, "Two passwords don't match !",Toast.LENGTH_LONG).show();
                }



            }
        });
    }
}