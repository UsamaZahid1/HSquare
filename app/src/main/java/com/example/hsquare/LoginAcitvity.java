package com.example.hsquare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.CheckBox;

import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsquare.Model.Users;
import com.example.hsquare.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class LoginAcitvity extends AppCompatActivity {
    private EditText etNumber, etPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private String parentDbName = "Users";
    private CheckBox cbRemember;
    TextView tvForgetPassword;

    ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNumber = findViewById(R.id.et_login_number);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        cbRemember = findViewById(R.id.cb_login_rememberme);
        progressDialog = new ProgressDialog(this);
        ivBack = findViewById(R.id.iv_login_back);
        tvForgetPassword = findViewById(R.id.tv_login_forgetpassword);

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAcitvity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LoginAcitvity.this, MainActivity.class);
                startActivity(in);
            }
        });


        Paper.init(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String number = etNumber.getText().toString();
        String password = etPassword.getText().toString();

        if (number.isEmpty()) {
            Toast.makeText(this, "Your Phone Number required! can't be empty...", Toast.LENGTH_LONG).show();
            etNumber.setError("Your Phone Number Section can't be empty");
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is required! can't be empty...", Toast.LENGTH_LONG).show();
            etPassword.setError("Password required!");

        } else {
            progressDialog.setTitle("Login Account");
            progressDialog.setMessage("Please wait while we are checking credential!");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            allowAccessToAccount(number, password);
        }
    }

    private void allowAccessToAccount(final String number, final String password) {

        if (cbRemember.isChecked()) {
            Paper.book().write(Prevalent.userPhoneKey, number);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(parentDbName).child(number).exists()) {
                    Users usersData = snapshot.child(parentDbName).child(number).getValue(Users.class);
                    if (usersData.getPhone().equals(number)) {

                        if (usersData.getPassword().equals(password)) {
                            Toast.makeText(LoginAcitvity.this, "Logged in Successfully...", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(LoginAcitvity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);


                        } else {
                            Toast.makeText(LoginAcitvity.this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }

                } else {
                    Toast.makeText(LoginAcitvity.this, "Account don't exists! Create a new Account!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}