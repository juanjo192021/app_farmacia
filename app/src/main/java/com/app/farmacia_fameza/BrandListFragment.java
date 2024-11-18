package com.app.farmacia_fameza;

import android.os.Bundle;

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

import com.app.farmacia_fameza.adapters.brandListAdapter;
import com.app.farmacia_fameza.business.bBrand;
import com.app.farmacia_fameza.databinding.FragmentBrandListBinding;
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.view.ProductListFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;
import java.util.List;

public class BrandListFragment extends Fragment implements brandListAdapter.OnBrandClickListener{

    List<ItemListDTO> listBrands;
    bBrand BBrand;
    brandListAdapter brandListAdapter;

    private FragmentBrandListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentBrandListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
    public void onBrandClick(ItemListDTO brand, View v) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                getActivity(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext())
                .inflate(
                        R.layout.modal_collection,
                        (LinearLayout) getView().findViewById(R.id.bottomCollectionModal)
                );
        TextView modaltitle = bottomSheetView.findViewById(R.id.txtCollectionModal);
        TextView modalBrandName = bottomSheetView.findViewById(R.id.txtNameCollectionModal);
        TextView modalBrandStatus = bottomSheetView.findViewById(R.id.txtStatusCollectionModal);
        TextView modalBrandCountProduct = bottomSheetView.findViewById(R.id.txtCountProductCollectionModal);

        modaltitle.setText("Ver Marca");
        modalBrandName.setText(brand.getName());
        modalBrandStatus.setText(brand.getStatus());
        modalBrandCountProduct.setText(String.valueOf(brand.getCount_Product()));


        bottomSheetView.findViewById(R.id.btnEditOfCollection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Si es el fragmento de CRUD de categorías
                Bundle bundle = new Bundle();

                // Agregar argumentos específicos para el CategoryCrudFragment
                bundle.putInt("idBrand", brand.getId());
                bundle.putString("nameBrand", brand.getName());
                bundle.putString("statusBrand", brand.getStatus());
                bundle.putString("quantityBrand", String.valueOf(brand.getCount_Product()));
                bundle.putBoolean("isEditFrame", true);

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.brandCrudFragment, bundle);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetView.findViewById(R.id.btnListProductOfCollection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ProductListDTO> products = BBrand.getProductsByBrand(brand.getId());

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
}