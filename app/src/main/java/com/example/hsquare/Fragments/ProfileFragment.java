package com.example.hsquare.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hsquare.Prevalent.Prevalent;
import com.example.hsquare.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    CircleImageView profileImageView;
    EditText etFullName, etNumber, etAddress;
    Button btnUpdate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = view.findViewById(R.id.civ_profileimage);
        etFullName = view.findViewById(R.id.et_profile_name);
        etNumber = view.findViewById(R.id.et_profile_number);
        etAddress = view.findViewById(R.id.et_profile_adress);
        btnUpdate = view.findViewById(R.id.btn_profile_update);

        userInfoDisplay(profileImageView, etFullName, etNumber, etAddress);

        return view;


    }

    private void userInfoDisplay(CircleImageView profileImageView, EditText etFullName, EditText etNumber, EditText etAddress) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("image").exists()){
                        String img=snapshot.child("image").getValue().toString();
                        String name=snapshot.child("name").getValue().toString();
                        String pass=snapshot.child("password").getValue().toString();
                        String address=snapshot.child("address").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
