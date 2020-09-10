package com.example.hsquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPasswordSucessMessage extends AppCompatActivity {
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_sucess_message);

        btnLogin=findViewById(R.id.btn_passwordsucces_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgotPasswordSucessMessage.this,LoginAcitvity.class);
                startActivity(intent);
            }
        });

    }
}