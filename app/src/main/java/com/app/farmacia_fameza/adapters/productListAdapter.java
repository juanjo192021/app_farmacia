package com.app.farmacia_fameza.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.dto.ProductListDTO;

import java.util.ArrayList;
import java.util.List;

public class productListAdapter extends RecyclerView.Adapter<itemViewHolder> {
    private final List<ProductListDTO> productList;
    private final LayoutInflater mInflater;
    private final List<ProductListDTO> productListFull;
    private final OnProductClickListener listener;

    public productListAdapter(List<ProductListDTO> products, Context context, OnProductClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.productList = products;
        this.productListFull = new ArrayList<>(products); // Guarda la lista original
        this.listener = listener; // Asigna el listener recibido
    }

    // Define la interfaz
    public interface OnProductClickListener {
        void onProductClick(ProductListDTO product, View v);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_item_list, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        ProductListDTO product = productList.get(position);
        holder.bindData(product);

        // Configura el click del producto usando la interfaz
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductClick(product, v);

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        if (TextUtils.isEmpty(text)) {
            productList.clear();
            productList.addAll(productListFull); // Si el texto está vacío, muestra todos los elementos
        } else {
            List<ProductListDTO> filteredList = new ArrayList<>();
            for (ProductListDTO product : productListFull) {
                if (product.getName().toLowerCase().contains(text.toLowerCase())) { // Filtra por nombre
                    filteredList.add(product);
                }
            }
            productList.clear();
            productList.addAll(filteredList);
        }
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

}