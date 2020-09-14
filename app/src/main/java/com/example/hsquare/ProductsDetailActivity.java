package com.example.hsquare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.hsquare.Fragments.HomeFragment;
import com.example.hsquare.Model.Products;
import com.example.hsquare.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProductsDetailActivity extends AppCompatActivity {

    private TextView tvPname, tvPdesc, tvPprice;
    private ImageView ivPimg;
    private ElegantNumberButton numberButton;
    private Button btnAddToCart;
    private String productId, state = "normal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_detail);

        productId = getIntent().getStringExtra("pid");

        tvPname = findViewById(R.id.tv_products_detail_pname);
        tvPdesc = findViewById(R.id.tv_products_detail_pdesc);
        tvPprice = findViewById(R.id.tv_products_detail_pprice);
        ivPimg = findViewById(R.id.iv_products_detail_productsimg);
        btnAddToCart = findViewById(R.id.btn_products_details_addtocart);
        numberButton = findViewById(R.id.btn_products_detail_numberbutton);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (state.equals("Order Placed") || state.equals("Orders Sipped")) {
                    Toast.makeText(ProductsDetailActivity.this, "You can place more products, once your order is shipped or confirmed!", Toast.LENGTH_LONG).show();
                } else {
                    addingToCartList();
                }
            }
        });

        getProductDetails(productId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrdersState();
    }

    private void addingToCartList() {

        String saveCurrentTime, saveCurrentDate;

        Calendar callForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        final DatabaseReference cartListRefernce = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productId);
        cartMap.put("pname", tvPname.getText().toString());
        cartMap.put("price", tvPprice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount", "");

        if (Singleton.obj.googleId == null) {

            cartListRefernce.child("Users Cart").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                    .child(productId)
                    .updateChildren(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                cartListRefernce.child("Admin Cart").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                                        .child(productId)
                                        .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ProductsDetailActivity.this, "Added to Cart List...", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProductsDetailActivity.this, HomeActivity.class);
                                        startActivity(intent);

                                    }
                                });
                            }
                        }
                    });
        } else {
            cartListRefernce.child("Google Users").child(Singleton.obj.googleId).child("Products")
                    .child(productId)
                    .updateChildren(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                cartListRefernce.child("Admin Cart").child("Google Users").child(Singleton.obj.googleId).child("Products")
                                        .child(productId)
                                        .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ProductsDetailActivity.this, "Added to Cart List...", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProductsDetailActivity.this, HomeActivity.class);
                                        startActivity(intent);

                                    }
                                });
                            }
                        }
                    });
        }
    }

    private void getProductDetails(String pid) {
        DatabaseReference productReference = FirebaseDatabase.getInstance().getReference().child("Products");
        productReference.child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products products = snapshot.getValue(Products.class);

                    tvPname.setText(products.getPname());
                    tvPdesc.setText(products.getDescription());
                    tvPprice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(ivPimg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkOrdersState() {
        DatabaseReference reference;
        if (Singleton.obj.googleId == null) {
            reference = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String shippingState = snapshot.child("state").getValue().toString();
                        String UserName = snapshot.child("name").getValue().toString();

                        if (shippingState.equals("shipped")) {

                            state = "Order Shipped";
                        } else if (shippingState.equals("not shipped")) {
                            state = "Order Placed";
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            reference = FirebaseDatabase.getInstance().getReference().child("Orders").child("Google Users").child(Singleton.obj.googleId);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String shippingState = snapshot.child("state").getValue().toString();
                        String UserName = snapshot.child("name").getValue().toString();

                        if (shippingState.equals("shipped")) {

                            state = "Order Shipped";
                        } else if (shippingState.equals("not shipped")) {
                            state = "Order Placed";
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(ProductsDetailActivity.this, HomeActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }
}
