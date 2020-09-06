package com.example.hsquare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.hsquare.Fragments.CartFragment;
import com.example.hsquare.Fragments.HomeFragment;
import com.example.hsquare.Fragments.JeansFragment;
import com.example.hsquare.Fragments.KurtaFragment;
import com.example.hsquare.Fragments.LogoutFragment;
import com.example.hsquare.Fragments.ProfileFragment;
import com.example.hsquare.Fragments.SearchFragment;
import com.example.hsquare.Fragments.ShalwarKameezFragment;
import com.example.hsquare.Fragments.TShirtFragment;
import com.example.hsquare.Model.Products;
import com.example.hsquare.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity implements LifecycleOwner {
    BottomNavigationView navigationView;
    ImageSlider imageSlider;
    LinearLayout layout_tab;
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