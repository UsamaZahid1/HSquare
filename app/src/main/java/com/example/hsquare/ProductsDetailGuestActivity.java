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
import com.example.hsquare.Model.Products;
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

public class ProductsDetailGuestActivity extends AppCompatActivity {

    private TextView tvPname, tvPdesc, tvPprice;
    private ImageView ivPimg;
    private ElegantNumberButton numberButton;
    private Button btnAddToCart;
    private String productId, guestId;
    private static String guestTime, guestDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_guestdetail);



        productId = getIntent().getStringExtra("pid");

        tvPname = findViewById(R.id.tv_products_guestdetail_pname);
        tvPdesc = findViewById(R.id.tv_products_guestdetail_pdesc);
        tvPprice = findViewById(R.id.tv_products_guestdetail_pprice);
        ivPimg = findViewById(R.id.iv_products_guestdetail_productsimg);
        btnAddToCart = findViewById(R.id.btn_products_guestdetails_addtocart);
        numberButton = findViewById(R.id.btn_products_guestdetail_numberbutton);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addingToCartList();
            }
        });

        getProductDetails(productId);
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

        cartListRefernce.child("Guest Cart").child(Singleton.obj.guestid).child("Products")
                .child(productId)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            cartListRefernce.child("Admin Cart").child("Guest").child(Singleton.obj.guestid).child("Products")
                                    .child(productId)
                                    .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ProductsDetailGuestActivity.this, "Added to Cart List...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ProductsDetailGuestActivity.this, HomeGuestAcitvity.class);
                                    startActivity(intent);

                                }
                            });
                        }
                    }
                });


    }

    private void getProductDetails(String pid) {
        DatabaseReference productReference = FirebaseDatabase.getInstance().getReference().child("Products");
        Log.d("stag", pid);
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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Singleton.obj.guestid= UUID.randomUUID().toString();
//    }
}

