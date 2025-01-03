package com.app.farmacia_fameza;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.farmacia_fameza.business.bBrand;
import com.app.farmacia_fameza.business.bCategory;
import com.app.farmacia_fameza.business.bProduct;
import com.app.farmacia_fameza.dto.ProductAddDTO;
import com.app.farmacia_fameza.dto.ProductUpdateDTO;
import com.app.farmacia_fameza.models.Product;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;


public class ProductDetailFragment extends Fragment {

    public static final String ARG_PRODUCT = "product";
    private Product product;
    Context context;
    com.app.farmacia_fameza.business.bCategory bCategory;
    com.app.farmacia_fameza.business.bBrand bBrand;
    com.app.farmacia_fameza.business.bProduct bProduct;
    Button btnUpdateProduct;
    EditText name,description,price,link;
    Spinner brand,category,status;
    ImageView image;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bBrand = new bBrand(getContext());
        bProduct = new bProduct(getContext());
        bCategory = new bCategory(getContext());
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable(ARG_PRODUCT); // Recupera el producto del Bundle
            String title = product != null ? product.getName() : null;
            if (getActivity() != null) {
                getActivity().setTitle(title);
            }
        }
    }

    // Método para configurar el Spinner
    public void setupBrandSpinner(Spinner spinner) {
        List<String> brands = bBrand.getBrandsName();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, brands);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void setupCategorySpinner(Spinner spinner) {
        List<String> brands = bCategory.getCategoryName();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, brands);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void setupStatusSpinner(Spinner spinner) {
        List<String> statusOptions = Arrays.asList("Activo", "Inactivo");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void setSpinnerToValue(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int position = adapter.getPosition(value);
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        String valStatus;

        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);

        name = root.findViewById(R.id.txtEditProductDetailName);
        description = root.findViewById(R.id.txtEditProductDetailDescription);
        link = root.findViewById(R.id.txtEditProductDetailImage);
        price = root.findViewById(R.id.txtxEditProductDetailPrice);
        brand = root.findViewById(R.id.spinnerEditProductDetailBrand);
        setupBrandSpinner(brand);

        category = root.findViewById(R.id.spinnerProductDetailCategory);
        setupCategorySpinner(category);

        status = root.findViewById(R.id.spinnerEditProductDetailStatus);
        setupStatusSpinner(status);

        image = root.findViewById(R.id.imageEditProductDetail);

        if(product.getStatus() == 1){
            valStatus = "Activo";
        }else{
            valStatus = "Inactivo";
        }

        if(product != null){
            name.setText(product.getName());
            description.setText(product.getDescription());
            link.setText(product.getImage());
            price.setText(String.valueOf(product.getUnit_price()));
            setSpinnerToValue(brand,product.getBrand().getName());
            setSpinnerToValue(category,product.getCategory().getName());
            setSpinnerToValue(status,valStatus);
            Picasso.get().load(product.getImage()).into(image);
        }

        btnUpdateProduct = root.findViewById(R.id.btnDetailProductUpdate);
        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {updateProduct();}
        });
        return root;
    }

    private void updateProduct(){
        ProductUpdateDTO productUpdateDTO = completeDataUpdateProduct();
        context = requireActivity();
        boolean band = bProduct.existsProductById(productUpdateDTO.getId());
        if(band){
            bProduct.updateProduct(productUpdateDTO);
            Toast.makeText(context, "Producto Actualizado OK", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Producto No Encontrado", Toast.LENGTH_LONG).show();
        }
    }

    private ProductUpdateDTO completeDataUpdateProduct(){
        Integer numberStatus;
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
        Integer id = product.getId();
        String n = name.getText().toString();
        String d = description.getText().toString();
        String l = link.getText().toString();
        String p = price.getText().toString();
        String b = brand.getSelectedItem().toString();
        String c = category.getSelectedItem().toString();

        if(status.getSelectedItem().toString().equals("Activo")){
            numberStatus = 1;
        }else{
            numberStatus = 0;
        }

        productUpdateDTO.setId(id);
        productUpdateDTO.setName(n);
        productUpdateDTO.setDescription(d);
        productUpdateDTO.setImage(l);
        productUpdateDTO.setUnit_price(Double.parseDouble(p));
        productUpdateDTO.setBrand(b);
        productUpdateDTO.setCategory(c);
        productUpdateDTO.setStatus(numberStatus);

        return productUpdateDTO;
    }
}