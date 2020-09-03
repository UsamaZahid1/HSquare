package com.example.hsquare.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.hsquare.Model.Products;
import com.example.hsquare.ProductsDetailActivity;
import com.example.hsquare.R;
import com.example.hsquare.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchFragment extends Fragment {

    EditText etSearch;
    Button btnSearch;
    RecyclerView recyclerView;
    private String searchInput;
    Toolbar toolbar;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);
//
        etSearch = view.findViewById(R.id.et_search_search);
        btnSearch = view.findViewById(R.id.iv_search);
        recyclerView = view.findViewById(R.id.rv_search);
        // toolbar = view.findViewById(R.id.toolbar);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = etSearch.getText().toString();
                onStart();
            }
        });

        return view;
    }


    public void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("pname").startAt(searchInput).endAt(searchInput+"\uf8ff"), Products.class)
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

                        Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
                        intent.putExtra("pid", model.getPid());
                        startActivity(intent);
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


//    @Override
//    public void onPrepareOptionsMenu(@NonNull Menu menu) {
//        getActivity().getMenuInflater().inflate(R.menu.search_menu, menu);
//        MenuItem item = menu.findItem(R.id.search_id);
//        SearchView searchView = (SearchView) item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                search(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                search(s);
//                return false;
//            }
//        });
//        super.onPrepareOptionsMenu(menu);
//    }
}
