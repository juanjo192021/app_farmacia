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

public class categoryListAdapter extends RecyclerView.Adapter<itemViewHolder>{

    private List<ItemListDTO> categoryList;
    private final LayoutInflater mInflater;
    private final List<ItemListDTO> categoryListFull;
    private final OnCategoryClickListener listener;

    public categoryListAdapter(List<ItemListDTO> categories, Context context, OnCategoryClickListener listener) {
        this.categoryList = categories;
        this.mInflater = LayoutInflater.from(context);
        this.categoryListFull = new ArrayList<>(categories);
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(ItemListDTO category, View v);

    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {

        ItemListDTO category = categoryList.get(position);
        holder.bindData(category);

        // Configura el clic del producto usando la interfaz
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(category, v);

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        if (TextUtils.isEmpty(text)) {
            categoryList.clear();
            categoryList.addAll(categoryListFull); // Si el texto está vacío, muestra todos los elementos
        } else {
            List<ItemListDTO> filteredList = new ArrayList<>();
            for (ItemListDTO category : categoryListFull) {
                if (category.getName().toLowerCase().contains(text.toLowerCase())) { // Filtra por nombre
                    filteredList.add(category);
                }
            }
            categoryList.clear();
            categoryList.addAll(filteredList);
        }
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

}
