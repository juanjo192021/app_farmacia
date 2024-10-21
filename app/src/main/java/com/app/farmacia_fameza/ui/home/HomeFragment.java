package com.app.farmacia_fameza.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.farmacia_fameza.ProductDetailFragment;
import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.adapters.ListAdapter;
import com.app.farmacia_fameza.business.bProduct;
import com.app.farmacia_fameza.databinding.FragmentHomeBinding;
import com.app.farmacia_fameza.models.Product;


import java.util.List;

public class HomeFragment extends Fragment implements ListAdapter.OnProductClickListener{

    private FragmentHomeBinding binding;

    List<Product> listProducts;
    bProduct BProduct;
    ListAdapter listAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicializar bProduct sin pasar el contexto directamente en la declaración
        BProduct = new bProduct(getContext());

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Inicializar la interfaz
        init();
        // Configurar el EditText para el filtrado
        EditText searchEditText = binding.searchEditText;
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listAdapter.filter(s.toString()); // Llama al método de filtrado
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
    }

    public void init(){

        listProducts = BProduct.showProducts();

        if (listProducts != null && !listProducts.isEmpty()) {
            for (Product product : listProducts) {
                Log.d("HomeFragment", "Producto: " + product.getName()); // Asumiendo que has sobrescrito toString() en el modelo Product
            }
        } else {
            Log.d("HomeFragment", "No hay productos disponibles.");
        }

        listAdapter = new ListAdapter(listProducts,this.getContext(),this);
        RecyclerView recyclerView = binding.recyclerViewProducts;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void onProductClick(Product product, View v) {

        // Obtener el NavController
        NavController navController = Navigation.findNavController(v);

        // Crear un Bundle para pasar el producto
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProductDetailFragment.ARG_PRODUCT, product); // Asegúrate de que Product implemente Serializable

        // Navegar al fragmento de detalles
        navController.navigate(R.id.productDetailFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}