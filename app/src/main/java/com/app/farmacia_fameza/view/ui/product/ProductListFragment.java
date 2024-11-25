package com.app.farmacia_fameza.view.ui.product;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.adapters.productListAdapter;
import com.app.farmacia_fameza.business.bProduct;
import com.app.farmacia_fameza.databinding.FragmentListProductBinding;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

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

    @Override
    public void onResume() {
        super.onResume();
        getProducts(); // Recargar los datos cada vez que la vista vuelve a ser visible
        Log.d("MiAppLog", "Vista visible nuevamente");
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseAdapter(); // Liberar el adaptador al salir de la vista
        Log.d("MiAppLog", "Vista ya no está visible, recursos liberados");
    }

    private void releaseAdapter() {
        if (productListAdapter != null) {
            productListAdapter = null; // Elimina la referencia al adaptador
            binding.recyclerViewProduct.setAdapter(null); // Limpia el RecyclerView
            listProducts = null; // Elimina la referencia a la lista de productos
        }
    }

    public void getProducts(){
        // Intentar recuperar la lista de productos del Bundle

        if (getArguments() != null && getArguments().containsKey(ARG_PRODUCT_LIST)) {
            listProducts = (List<ProductListDTO>) getArguments().getSerializable(ARG_PRODUCT_LIST); // Recupera la lista de productos del Bundle
            Log.d("MiAppLog", "Productos cargados de categoría o marca");
        }
        if (listProducts == null) {
            listProducts = BProduct.getProducts(); // Obtener productos desde la base de datos
            Log.d("MiAppLog", "Productos cargados de producto");

        }

        productListAdapter = new productListAdapter(listProducts,this.getContext(),this);
        RecyclerView recyclerView = binding.recyclerViewProduct;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(productListAdapter);
    }

    public void onProductClick(ProductListDTO productDTO, View v) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                getActivity(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext())
                .inflate(
                        R.layout.modal_editproduct,
                        (LinearLayout) getView().findViewById(R.id.bottomLayoutEditProduct)
                );

        Product product = BProduct.getDetailProduct(productDTO.getId());
        TextView modalNameProduct = bottomSheetView.findViewById(R.id.txtModalNameProduct);
        TextView modalDescriptionProduct = bottomSheetView.findViewById(R.id.txtModalEditDescription);
        TextView modalPriceProduct = bottomSheetView.findViewById(R.id.txtModalEditPrice);
        TextView modalBrandProduct = bottomSheetView.findViewById(R.id.txtModalEditBrand);
        TextView modalCategoryProduct = bottomSheetView.findViewById(R.id.txtModalEditCategory);
        TextView modalStatusProduct = bottomSheetView.findViewById(R.id.txtModalEditStatus);
        TextView modalStockProduct = bottomSheetView.findViewById(R.id.txtModalEditStock);

        modalNameProduct.setText(product.getName());
        modalDescriptionProduct.setText(product.getDescription());
        modalPriceProduct.setText(BProduct.searchPriceProduct(product.getId()).toString());
        modalBrandProduct.setText(product.getBrand().getName());
        modalCategoryProduct.setText(product.getCategory().getName());
        modalStockProduct.setText(product.getStock_actual().toString());

        if(product.getStatus() == 1){
            modalStatusProduct.setText("Activo");
        }else{
            modalStatusProduct.setText("Inactivo");
        }

        RoundedImageView imageModalEditProduct = bottomSheetView.findViewById(R.id.imageModalEditProduct);
        String imageUrl = product.getImage();

        Glide.with(this)
                .load(imageUrl)
                //.placeholder(R.drawable.botonedit)
                //.error(R.drawable.error_image)
                .into(imageModalEditProduct);

        bottomSheetView.findViewById(R.id.btnModalEditProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProductDetailFragment.ARG_PRODUCT, (Serializable) product);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.productDetailFragment, bundle);
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