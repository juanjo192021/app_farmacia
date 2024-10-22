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
import com.app.farmacia_fameza.dto.ItemListDTO;

import java.util.ArrayList;
import java.util.List;


public class brandListAdapter extends RecyclerView.Adapter<itemViewHolder>{

    private final List<ItemListDTO> brandList;
    private final LayoutInflater mInflater;
    private final List<ItemListDTO> brandListFull;
    private final OnBrandClickListener listener;

    public brandListAdapter(List<ItemListDTO> brands, Context context, OnBrandClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.brandList = brands;
        this.brandListFull = new ArrayList<>(brands); // Guarda la lista original
        this.listener = listener; // Asigna el listener recibido

    }

    public interface OnBrandClickListener {
        void onBrandClick(ItemListDTO brand, View v);
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        ItemListDTO brand = brandList.get(position);
        holder.bindData(brand);

        // Configura el clic del producto usando la interfaz
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBrandClick(brand, v);

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        if (TextUtils.isEmpty(text)) {
            brandList.clear();
            brandList.addAll(brandListFull); // Si el texto está vacío, muestra todos los elementos
        } else {
            List<ItemListDTO> filteredList = new ArrayList<>();
            for (ItemListDTO brand : brandListFull) {
                if (brand.getName().toLowerCase().contains(text.toLowerCase())) { // Filtra por nombre
                    filteredList.add(brand);
                }
            }
            brandList.clear();
            brandList.addAll(filteredList);
        }
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

}
