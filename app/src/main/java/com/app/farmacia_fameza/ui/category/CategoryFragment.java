package com.app.farmacia_fameza.ui.category;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.app.farmacia_fameza.adapters.categoryListAdapter;
import com.app.farmacia_fameza.business.bCategory;
import com.app.farmacia_fameza.databinding.FragmentCategoryBinding;
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;
import com.app.farmacia_fameza.view.ProductListFragment;

import java.io.Serializable;
import java.util.List;

public class CategoryFragment extends Fragment implements categoryListAdapter.OnCategoryClickListener {

    List<ItemListDTO> listCategories;
    bCategory BCategory;
    categoryListAdapter categoryListAdapter;

    private FragmentCategoryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar BProduct
        BCategory = new bCategory(getContext());

        getCategories();
        setupSearchFilter();

        return root;
    }

    private void setupSearchFilter() {
        EditText searchEditText = binding.searchEditTextCategory;
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                categoryListAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void getCategories(){
        listCategories = BCategory.getCategories();

        if (listCategories != null && !listCategories.isEmpty()) {
            for (ItemListDTO category : listCategories) {
                Log.d("CategoryFragment", "Categoria: " + category.getName());
            }
        } else {
            Log.d("CategoryFragment", "No hay categorias disponibles.");
        }

        categoryListAdapter = new categoryListAdapter(listCategories,this.getContext(),this);
        RecyclerView recyclerView = binding.recyclerViewCategory;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(categoryListAdapter);
    }

    @Override
    public void onCategoryClick(ItemListDTO category, View v) {
        // Obtener la lista de productos de la categoría seleccionada

        List<ProductListDTO> products = BCategory.getProductsByCategory(category.getId());

        // Obtener el NavController
        NavController navController = Navigation.findNavController(v);

        // Crear un Bundle para pasar la lista de productos
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProductListFragment.ARG_PRODUCT_LIST, (Serializable) products);

        // Navegar al fragmento de detalles o a la lista de productos
        navController.navigate(R.id.productListFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}