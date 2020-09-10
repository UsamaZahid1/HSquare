package com.example.hsquare.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.hsquare.Adapter.ViewPagerAdapter;
import com.example.hsquare.HomeActivity;
import com.example.hsquare.Model.Products;
import com.example.hsquare.ProductsDetailActivity;
import com.example.hsquare.R;
import com.example.hsquare.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


public class HomeFragment extends Fragment {

    ImageSlider imageSlider;
    private DatabaseReference productReference;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.imageslider_home);
        recyclerView = view.findViewById(R.id.recyclerview_home);


//---------------------------------imageSlier---------------------------------------
        final List<SlideModel> slideModel = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ImageSlider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            slideModel.add(new SlideModel(dataSnapshot.child("url")
                                    .getValue().toString(), dataSnapshot.child("title").getValue().toString(), ScaleTypes.FIT));
                        }
                        imageSlider.setImageList(slideModel, ScaleTypes.FIT);
                        imageSlider.startSliding(1500);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        productReference = FirebaseDatabase.getInstance().getReference().child("Products");


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(productReference, Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                holder.tvProductName.setText(model.getPname());
                holder.tvProductPrice.setText("PKR. " + model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.ivProductimg);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getContext(), ProductsDetailActivity.class);
                        intent.putExtra("pid", model.getPid());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);

                return new ProductViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.startListening();

    }


}
