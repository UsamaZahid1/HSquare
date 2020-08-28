package com.example.hsquare.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsquare.R;
import com.example.hsquare.interfaces.ItemClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvProductName, tvProductPrice;
    public ImageView ivProductimg;
    public ItemClickListener listener;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        tvProductName = itemView.findViewById(R.id.tv_productListItem_productname);
        ivProductimg = itemView.findViewById(R.id.iv_productListItem_productimg);
        tvProductPrice = itemView.findViewById(R.id.tv_productListItem_productprice);

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
