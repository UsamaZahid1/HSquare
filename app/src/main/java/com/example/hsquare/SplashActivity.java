package com.example.hsquare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    int SPLASH_TIME = 4000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mySuperIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mySuperIntent);

            }
        }, SPLASH_TIME);
    }
}
