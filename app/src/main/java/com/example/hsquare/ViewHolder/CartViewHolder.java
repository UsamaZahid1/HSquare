package com.example.hsquare.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.hsquare.R;
import com.example.hsquare.interfaces.ItemClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvPname, tvprice, tvquantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        tvPname = itemView.findViewById(R.id.tv_cartlistitem_productname);
        tvprice = itemView.findViewById(R.id.tv_cartlistitem_productprice);
        tvquantity = itemView.findViewById(R.id.tv_cartlistitem_productquantity);
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
