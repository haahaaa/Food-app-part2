package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button signBtn, loginBtn;
    private EditText username, password;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signBtn = findViewById(R.id.signBtn);
        loginBtn = findViewById(R.id.loginBtn);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.passWord);
        db = new DatabaseHelper(this);


        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, signup.class);
                startActivity(i);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pw = password.getText().toString().trim();


                Boolean result = db.checkUser(user,pw);
                if(result == true){
                    Toast.makeText(MainActivity.this, "Successfully logged in",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, homepage.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this, "Login error!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}