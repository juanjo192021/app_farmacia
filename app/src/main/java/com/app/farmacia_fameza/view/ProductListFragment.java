package com.app.farmacia_fameza.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.farmacia_fameza.ProductDetailFragment;
import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.adapters.productListAdapter;
import com.app.farmacia_fameza.business.bProduct;
import com.app.farmacia_fameza.databinding.FragmentListProductBinding;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;

import java.io.Serializable;
import java.util.List;

public class ProductListFragment extends Fragment implements productListAdapter.OnProductClickListener{

    public static final String ARG_PRODUCT_LIST = "list_product";
    private FragmentListProductBinding binding;

    List<ProductListDTO> listProducts;
    bProduct BProduct;
    com.app.farmacia_fameza.adapters.productListAdapter productListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentListProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar BProduct
        BProduct = new bProduct(getContext());

        getProducts();
        setupSearchFilter();

        return root;
    }

    private void setupSearchFilter() {
        EditText searchEditText = binding.searchEditTextProduct;
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar nada aquí
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Llama al método de filtrado en el adaptador de la lista
                productListAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar nada aquí
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void getProducts(){

        // Intentar recuperar la lista de productos del Bundle
        if (getArguments() != null && getArguments().containsKey(ARG_PRODUCT_LIST)) {
            listProducts = (List<ProductListDTO>) getArguments().getSerializable(ARG_PRODUCT_LIST); // Recupera la lista de productos del Bundle
        }

        if (listProducts == null || listProducts.isEmpty()) {
            listProducts = BProduct.getProducts(); // Obtener productos desde la base de datos
        }

        productListAdapter = new productListAdapter(listProducts,this.getContext(),this);
        RecyclerView recyclerView = binding.recyclerViewProduct;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(productListAdapter);
    }

    public void onProductClick(ProductListDTO productDTO, View v) {

        Product product = BProduct.getDetailProduct(productDTO.getId());

        // Obtener el NavController
        NavController navController = Navigation.findNavController(v);

        // Crear un Bundle para pasar el producto
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProductDetailFragment.ARG_PRODUCT, (Serializable) product);

        // Navegar al fragmento de detalles
        navController.navigate(R.id.productDetailFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}