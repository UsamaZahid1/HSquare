package com.example.hsquare;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.hsquare.Fragments.CartFragment;
import com.example.hsquare.Fragments.HomeFragment;
import com.example.hsquare.Fragments.LogoutFragment;
import com.example.hsquare.Fragments.ProfileFragment;
import com.example.hsquare.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

public class HomeActivity extends AppCompatActivity implements LifecycleOwner {
    BottomNavigationView navigationView;
    FrameLayout frameLayout;

    private DatabaseReference productReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        navigationView = findViewById(R.id.bottom_nav_view);
        frameLayout = findViewById(R.id.layout_frame);


        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame,
                new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_logout:
                        fragment = new LogoutFragment();
                        break;

                    case R.id.nav_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.nav_cart:
                        fragment = new CartFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, fragment).commit();

                return true;
            }
        });


    }

}