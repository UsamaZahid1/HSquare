package com.example.hsquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hsquare.Prevalent.Prevalent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    Button btnUpdate;
    EditText etSetNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        etSetNewPassword = findViewById(R.id.et_setnewPassword_password);
        btnUpdate = findViewById(R.id.btn_setNewPassword_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewPassword();
            }
        });
    }

    private void setNewPassword() {
        String newPassword = etSetNewPassword.getText().toString();
        String phone=getIntent().getStringExtra("phone");

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");
        reference.child(phone).child("password").setValue(newPassword);

        startActivity(new Intent(getApplicationContext(),ForgotPasswordSucessMessage.class));
        finish();

    }
}