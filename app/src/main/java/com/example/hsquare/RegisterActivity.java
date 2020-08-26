package com.example.hsquare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etNumber, etPassword, etConfirmP;
    Button btnRegister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        etName = findViewById(R.id.et_register_name);
        etNumber = findViewById(R.id.et_register_number);
        etPassword = findViewById(R.id.et_register_password);
        // etConfirmP=findViewById(R.id.et_register_confirmp);
        btnRegister = findViewById(R.id.btn_register_register);
        progressDialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String name = etName.getText().toString();
        String number = etNumber.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Your Name is required! can't be empty...", Toast.LENGTH_LONG).show();
            etName.setError("Your Name Section can't be empty!");

        } else if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Your Phone Number required! can't be empty...", Toast.LENGTH_LONG).show();
            etNumber.setError("Your Phone Number Section can't be empty");

        } else if (!Patterns.PHONE.matcher(number).matches()) {
            //Toast.makeText(this, "Please enter a valid number address...", Toast.LENGTH_SHORT).show();
            etNumber.setError("Invalid Phone Number!");

        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is required! can't be empty...", Toast.LENGTH_LONG).show();
            etPassword.setError("Password required!");

        } else if (password.length() < 6) {
            etPassword.setError("Password length short, Minimum 6 character required!");

        } else {
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Please wait while we are checking credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            validateNumber(name, number, password);
        }
    }

    private void validateNumber(final String name, final String phone, final String password) {

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if this phone number not exists in the databse then...
                if ((!snapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone);
                    userDataMap.put("password", password);
                    userDataMap.put("name", name);

                    rootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Congratulation, your account as been created...", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginAcitvity.class);
                                        startActivity(intent);
                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network error please try again.!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                } else {
                    Toast.makeText(RegisterActivity.this, phone + " already exists, try again using another Number", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
