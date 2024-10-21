package com.app.farmacia_fameza.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Product> productList;
    private LayoutInflater mInflater;
    private Context context;
    private List<Product> productListFull;
    private OnProductClickListener listener;

    public ListAdapter(List<Product> products, Context context, OnProductClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.productList = products;
        this.productListFull = new ArrayList<>(products); // Guarda la lista original
        this.listener = listener; // Asigna el listener recibido

    }

    // Define la interfaz
    public interface OnProductClickListener {
        void onProductClick(Product product, View v);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bindData(product);

        holder.name.setText(product.getName());

        // Configura el clic del producto usando la interfaz
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductClick(product, v);

            }
        });
    }

    public void filter(String text) {
        if (TextUtils.isEmpty(text)) {
            productList.clear();
            productList.addAll(productListFull); // Si el texto está vacío, muestra todos los elementos
        } else {
            List<Product> filteredList = new ArrayList<>();
            for (Product product : productListFull) {
                if (product.getName().toLowerCase().contains(text.toLowerCase())) { // Filtra por nombre
                    filteredList.add(product);
                }
            }
            productList.clear();
            productList.addAll(filteredList);
        }
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, stock, price;
        ViewHolder (View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.itemPhoto);
            name = itemView.findViewById(R.id.itemName);
            stock = itemView.findViewById(R.id.itemStock);
        }

        void bindData(final Product item) {
            name.setText(item.getName());
            stock.setText(String.valueOf(item.getStock_actual()));
        }
    }
}