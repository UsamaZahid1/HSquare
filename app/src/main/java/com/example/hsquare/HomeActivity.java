package com.example.hsquare;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.hsquare.Fragments.CartFragment;
import com.example.hsquare.Fragments.HomeFragment;
import com.example.hsquare.Fragments.OrdersFragment;
import com.example.hsquare.Fragments.ProfileFragment;
import com.example.hsquare.Fragments.SearchFragment;
import com.example.hsquare.Model.Products;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

public class HomeActivity extends AppCompatActivity implements LifecycleOwner {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.bottom_nav_view);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
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
                    case R.id.nav_orders:
                        fragment = new OrdersFragment();
                        break;
                    case R.id.nav_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.nav_cart:
                        fragment = new CartFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                return true;
            }
        });
        FirebaseRecyclerOptions<Products> options;

    }
}
