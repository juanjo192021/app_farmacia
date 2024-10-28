package com.app.farmacia_fameza;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.farmacia_fameza.models.Product;
import com.squareup.picasso.Picasso;


public class ProductDetailFragment extends Fragment {

    public static final String ARG_PRODUCT = "product";
    private Product product;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable(ARG_PRODUCT); // Recupera el producto del Bundle
            String title = product != null ? product.getName() : null;
            if (getActivity() != null) {
                getActivity().setTitle(title);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);


        // Inicializa las vistas
        TextView productNameTextView = root.findViewById(R.id.productNameTextView);
        TextView productDescriptionTextView = root.findViewById(R.id.productDescriptionTextView);
        TextView productStockTextView = root.findViewById(R.id.productStockTextView);
        TextView productPriceTextView = root.findViewById(R.id.productPriceTextView);
        TextView productBrandTextView = root.findViewById(R.id.productBrandTextView);
        TextView productCategoryTextView = root.findViewById(R.id.productCategoryTextView);
        ImageView imageDetailProduct = root.findViewById(R.id.productImageView);

        // Muestra los detalles del producto
        if (product != null) {
            productNameTextView.setText(product.getName());
            productDescriptionTextView.setText(product.getDescription());
            productStockTextView.setText(String.valueOf(product.getStock_actual()));
            productPriceTextView.setText(String.valueOf(product.getUnit_price()));
            productBrandTextView.setText(String.valueOf(product.getBrand().getName()));
            productCategoryTextView.setText(String.valueOf(product.getCategory().getName()));
            Picasso.get().load(product.getImage()).into(imageDetailProduct);
        }

        return root;
    }
}