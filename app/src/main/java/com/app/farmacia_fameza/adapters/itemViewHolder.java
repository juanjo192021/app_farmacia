package com.app.farmacia_fameza.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;

public class itemViewHolder extends RecyclerView.ViewHolder{

    TextView status, name, quantity;

    public itemViewHolder(@NonNull View itemView) {
        super(itemView);
        status = itemView.findViewById(R.id.itemStatus);
        name = itemView.findViewById(R.id.itemName);
        quantity = itemView.findViewById(R.id.itemQuantity);
    }

    public void bindData(ProductListDTO item) {
        name.setText(item.getName());
        quantity.setText(String.valueOf(item.getStock_actual()));
        status.setText(String.valueOf(item.getUnit_price()));
    }

    public void bindData(ItemListDTO item) {
        name.setText(item.getName());
        quantity.setText(String.valueOf(item.getCount_Product()));
        status.setText(item.getStatus());
    }

}
