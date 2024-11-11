package com.app.farmacia_fameza;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.farmacia_fameza.adapters.categoryListAdapter;
import com.app.farmacia_fameza.business.bCategory;
import com.app.farmacia_fameza.databinding.FragmentCategoryListBinding;
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.view.ProductListFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;
import java.util.List;

public class CategoryListFragment extends Fragment implements categoryListAdapter.OnCategoryClickListener {

    List<ItemListDTO> listCategories;
    bCategory BCategory;
    categoryListAdapter categoryListAdapter;

    private FragmentCategoryListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentCategoryListBinding.inflate(inflater, container, false);
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

        categoryListAdapter = new categoryListAdapter(listCategories,this.getContext(),this);
        RecyclerView recyclerView = binding.recyclerViewCategory;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(categoryListAdapter);
    }

    @Override
    public void onCategoryClick(ItemListDTO category, View v) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                getActivity(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext())
                .inflate(
                        R.layout.modal_category,
                        (LinearLayout) getView().findViewById(R.id.bottomCategoryModal)
                );

        TextView modalCategoryName = bottomSheetView.findViewById(R.id.txtNameCategoryModal);
        TextView modalCategoryStatus = bottomSheetView.findViewById(R.id.txtStatusCategoryModal);
        TextView modalCategoryCountProduct = bottomSheetView.findViewById(R.id.txtCountProductCategoryModal);

        modalCategoryName.setText(category.getName());
        modalCategoryStatus.setText(category.getStatus());
        modalCategoryCountProduct.setText(String.valueOf(category.getCount_Product()));

        bottomSheetView.findViewById(R.id.btnEditOfCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Si es el fragmento de CRUD de categorías
                Bundle bundle = new Bundle();

                // Agregar argumentos específicos para el CategoryCrudFragment
                bundle.putInt("idCategory", category.getId());
                bundle.putString("nameCategory", category.getName());
                bundle.putString("statusCategory", category.getStatus());
                bundle.putString("quantityCategory", String.valueOf(category.getCount_Product()));
                bundle.putBoolean("isEditFrame", true);

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.categoryCrudFragment, bundle);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetView.findViewById(R.id.btnListProductOfCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ProductListDTO> products = BCategory.getProductsByCategory(category.getId());

                // Obtener el NavController
                NavController navController = Navigation.findNavController(v);

                // Crear un Bundle para pasar la lista de productos
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProductListFragment.ARG_PRODUCT_LIST, (Serializable) products);

                // Navegar al fragmento de detalles o a la lista de productos
                navController.navigate(R.id.productListFragment, bundle);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}