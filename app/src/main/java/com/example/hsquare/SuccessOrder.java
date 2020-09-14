package com.example.hsquare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SuccessOrder extends AppCompatActivity {
    int SPLASH_TIME = 3000;
    ImageView done;

    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_order);

        done=findViewById(R.id.iv_check);

        Drawable drawable=done.getDrawable();

        if(drawable instanceof AnimatedVectorDrawableCompat){
            avd= (AnimatedVectorDrawableCompat) drawable;
            avd.start();
        }else if(drawable instanceof AnimatedVectorDrawable){
            avd2= (AnimatedVectorDrawable) drawable;
            avd2.start();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mySuperIntent = new Intent(SuccessOrder.this, HomeActivity.class);
                startActivity(mySuperIntent);

            }
        }, SPLASH_TIME);

    }
}