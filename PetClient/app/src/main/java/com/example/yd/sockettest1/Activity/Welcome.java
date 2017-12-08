package com.example.yd.sockettest1.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.yd.sockettest1.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
        @Override
             public void run() {
             Intent in = new Intent(Welcome.this, LoginActivity.class);
                startActivity(in);
            }
       }, 2000);
    }
}
