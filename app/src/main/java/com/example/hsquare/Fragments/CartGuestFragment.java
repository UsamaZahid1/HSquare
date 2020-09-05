package com.example.hsquare.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsquare.ConfirmOrderAcitvity;
import com.example.hsquare.HomeGuestAcitvity;
import com.example.hsquare.Model.Cart;
import com.example.hsquare.ProductsDetailGuestActivity;
import com.example.hsquare.R;
import com.example.hsquare.Singleton;
import com.example.hsquare.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CartGuestFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnProceed;
    private TextView tvTotalPrice, tvEmplty;
    private String guestId;
    private int overAllTotalPrice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guestcart, container, false);
        recyclerView = view.findViewById(R.id.rv_guestcart);
        btnProceed = view.findViewById(R.id.btn_guestcart_proceed);
        tvTotalPrice = view.findViewById(R.id.tv_guestcart_totalprice);
        tvEmplty = view.findViewById(R.id.tv_guestcart_ifemplty);

        guestId=getActivity().getIntent().getStringExtra("guest id");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConfirmOrderAcitvity.class);
                intent.putExtra("totalAmount", String.valueOf(overAllTotalPrice));
                startActivity(intent);
            }
        });

//checking if cart is empty or not
//        DatabaseReference checkRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
//        checkRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.hasChild("Guest Cart")) {
//
//
//                    btnProceed.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(getContext(), ConfirmOrderAcitvity.class);
//                            intent.putExtra("totalAmount", String.valueOf(overAllTotalPrice));
//                            startActivity(intent);
//                        }
//                    });
//
//                } else {
//                    btnProceed.setVisibility(View.GONE);
//                    tvEmplty.setVisibility(View.VISIBLE);
//
//                    //Toast.makeText(getContext(), "Cart is empty, Please Add some items to cart!", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        return view;
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//
//        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart List");
//
//        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
//                .setQuery(databaseReference.child("Guest Cart").child("Products").child(Singleton.obj.guestid), Cart.class)
//                .build();
//        Log.d("gtag",guestId);
//
//
//
//        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
//
//                holder.tvPname.setText(model.getPname());
//                holder.tvprice.setText("Price : " + model.getPrice());
//                holder.tvquantity.setText("Qty : " + model.getQuantity());
//
//                int singleItemTotalPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
//                overAllTotalPrice = overAllTotalPrice + singleItemTotalPrice;
//                tvTotalPrice.setText("Total: Rs. " + overAllTotalPrice);
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        CharSequence options[] = new CharSequence[]
//                                {
//                                        "Edit",
//                                        "Remove"
//                                };
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                        builder.setTitle("Cart options:");
//
//                        builder.setItems(options, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                if (i == 0) {
//                                    Intent intent = new Intent(getContext(), ProductsDetailGuestActivity.class);
//                                    intent.putExtra("pid", model.getPid());
//                                    startActivity(intent);
//                                }
//                                if (i == 1) {
//                                    databaseReference.child("Guest Cart")
//                                            .child("Products")
//                                            .child(model.getPid())
//                                            .removeValue()
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//
//                                                    if (task.isSuccessful()) {
//                                                        Toast.makeText(getActivity(), "Item removed successfully!", Toast.LENGTH_SHORT).show();
//                                                        Intent intent = new Intent(getContext(), HomeGuestAcitvity.class);
//                                                        startActivity(intent);
//                                                    }
//                                                }
//                                            });
//                                }
//                            }
//                        });
//                        builder.show();
//                    }
//
//
//                });
//            }
//
//            @NonNull
//            @Override
//            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_items, parent, false);
//
//                return new CartViewHolder(view);
//            }
//        };
//
//
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//
//
//    }
}


