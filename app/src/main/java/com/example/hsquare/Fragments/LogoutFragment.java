package com.example.hsquare.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hsquare.HomeActivity;
import com.example.hsquare.MainActivity;
import com.example.hsquare.R;
import com.example.hsquare.Singleton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import io.paperdb.Paper;

public class LogoutFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        CharSequence options[] = new CharSequence[]{
                "Yes", "No"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are You Sure You Want to Logout?");
        if (Singleton.obj.googleId == null) {


            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        Paper.book().destroy();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    if (i == 1) {
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
            builder.show();
        } else {

            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);

                    }
                    if (i == 1) {
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
            builder.show();

        }

        return view;
    }


}

