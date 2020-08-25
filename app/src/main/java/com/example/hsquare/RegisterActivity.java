package com.example.hsquare;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.rey.material.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmP;
    Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        etName = findViewById(R.id.et_register_name);
        etEmail = findViewById(R.id.et_register_Email);
        etPassword = findViewById(R.id.et_register_password);
        // etConfirmP=findViewById(R.id.et_register_confirmp);
        btnRegister = findViewById(R.id.btn_register_register);

//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             //   createAccount();
//            }
//        });

    }

//    private void createAccount() {
//        String name = etName.getText().toString();
//        String email = etEmail.getText().toString();
//        String password = etPassword.getText().toString();
//
//        if (TextUtils.isEmpty(name)) {
//            Toast.makeText(this, "Your Name is required! can't be empty...", Toast.LENGTH_LONG).show();
//            //etName.setError();
//
//        } else if (TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Your email required! can't be empty...", Toast.LENGTH_LONG).show();
//            //etName.setError();
//
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            //Toast.makeText(this, "Please enter a valid email address...", Toast.LENGTH_SHORT).show();
//            etEmail.setError("Invalid Email Address!");
//
//        } else if (TextUtils.isEmpty(password)) {
//            Toast.makeText(this, "Password is required! can't be empty...", Toast.LENGTH_LONG).show();
//            //etName.setError();
//
//        } else if (password.length() < 6) {
//            etPassword.setError("Password length short, Minimum 6 character required!");
//        }
//    }
}
