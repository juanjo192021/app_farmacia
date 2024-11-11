package com.app.farmacia_fameza.ui.brand;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.adapters.brandListAdapter;
import com.app.farmacia_fameza.business.bBrand;
import com.app.farmacia_fameza.databinding.FragmentBrandBinding;
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;
import com.app.farmacia_fameza.view.ProductListFragment;

import java.io.Serializable;
import java.util.List;

public class BrandFragment extends Fragment implements brandListAdapter.OnBrandClickListener{

    List<ItemListDTO> listBrands;
    bBrand BBrand;
    brandListAdapter brandListAdapter;

    private FragmentBrandBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentBrandBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar BProduct
        BBrand = new bBrand(getContext());

        getBrands();
        setupSearchFilter();

        return root;
    }

    private void setupSearchFilter() {
        EditText searchEditText = binding.searchEditTextBrand;
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar nada aquí
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Llama al método de filtrado en el adaptador de la lista
                brandListAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita implementar nada aquí
            }
        });
    }

    private void getBrands() {
        listBrands = BBrand.getBrands();

        brandListAdapter = new brandListAdapter(listBrands,this.getContext(),this);
        RecyclerView recyclerView = binding.recyclerViewBrand;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(brandListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onBrandClick(ItemListDTO brand, View v) {
        // Obtener la lista de productos de la categoría seleccionada

        List<ProductListDTO> products = BBrand.getProductsByBrand(brand.getId());

        // Obtener el NavController
        NavController navController = Navigation.findNavController(v);

        // Crear un Bundle para pasar la lista de productos
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProductListFragment.ARG_PRODUCT_LIST, (Serializable) products);

        // Navegar al fragmento de detalles o a la lista de productos
        navController.navigate(R.id.productListFragment, bundle);
    }
}