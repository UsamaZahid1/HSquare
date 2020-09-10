package com.example.hsquare;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hsquare.Fragments.CartGuestFragment;
import com.example.hsquare.Fragments.HomeGuestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class HomeGuestAcitvity extends AppCompatActivity {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_acitvity);


        navigationView = findViewById(R.id.bottom_nav_view_guest);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_guest_container,
                new HomeGuestFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home_guest);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                Fragment fragment = null;
                switch (item.getItemId()) {

                    case R.id.nav_home_guest:
                        fragment = new HomeGuestFragment();
                        break;

                    case R.id.nav_cart_guest:
                        fragment = new CartGuestFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_guest_container, fragment).commit();

                return true;
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
