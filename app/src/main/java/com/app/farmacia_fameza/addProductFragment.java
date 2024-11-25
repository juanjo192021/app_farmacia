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
import android.widget.Spinner;
import android.widget.Toast;

import com.app.farmacia_fameza.business.bProduct;
import com.app.farmacia_fameza.business.bBrand;
import com.app.farmacia_fameza.business.bCategory;
import com.app.farmacia_fameza.dto.ProductAddDTO;

import java.util.List;

public class addProductFragment extends Fragment{

    int id;
    Context context;
    bBrand bBrand;
    bProduct bProduct;
    bCategory bCategory;
    Button buttonAddProduct;
    EditText nameProduct, skuProduct, descriptionProduct, priceProduct, imageProduct;
    Spinner brandProduct, categoryProduct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bBrand = new bBrand(getContext());
        bProduct = new bProduct(getContext());
        bCategory = new bCategory(getContext());
    }

    // Método para configurar el Spinner
    public void setupBrandSpinner(Spinner spinner) {
        List<String> brands = bBrand.getBrandsName();

        // Crear un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, brands);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Configurar el Spinner con el adaptador
        spinner.setAdapter(adapter);
    }

    public void setupCategorySpinner(Spinner spinner) {
        List<String> brands = bCategory.getCategoryName();
        // Crear un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, brands);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Configurar el Spinner con el adaptador
        spinner.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        context = requireActivity();

        nameProduct = view.findViewById(R.id.txtRegisterNombre);
        skuProduct = view.findViewById(R.id.txtRegisterSKU);
        descriptionProduct = view.findViewById(R.id.txtRegisterDescription);
        priceProduct = view.findViewById(R.id.txtRegisterPrice);
        imageProduct = view.findViewById(R.id.txtRegisterImage);
        brandProduct = view.findViewById(R.id.spinnerRegisterBrand);
        setupBrandSpinner(brandProduct);

        skuProduct.setText(bProduct.generateNextSKU());

        categoryProduct = view.findViewById(R.id.spinnerRegisterCategory);
        setupCategorySpinner(categoryProduct);

        buttonAddProduct = view.findViewById(R.id.btnRegisterProduct);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { saveProduct();}
        });
        return view;
    }

    // Tener en cuenta que se definio el cabezado del url
    // solo seria necesario poner lo complementario
    // Ejem: v1732471969/Farmacia/Apronax_fmi0nl.png
    // para completar la ruta que obtenemos de nuestro repo de cloudinary
    private void saveProduct(){
        ProductAddDTO product = completeDataProduct();
        context = requireActivity();
        if(id == 0){
            bProduct.insertProduct(product);
            Toast.makeText(context, "Guardar OK", Toast.LENGTH_LONG).show();
        }
    }

    private ProductAddDTO completeDataProduct() {
        ProductAddDTO product = new ProductAddDTO();
        int idBrand =  bBrand.getIDBrand(brandProduct.getSelectedItem().toString());
        int idCategory =  bCategory.getIDCategory(categoryProduct.getSelectedItem().toString());

        String name = nameProduct.getText().toString();
        String sku = skuProduct.getText().toString();
        String description = descriptionProduct.getText().toString();
        String price = priceProduct.getText().toString();
        String image = imageProduct.getText().toString();
        String url = "https://res.cloudinary.com/dwx7qadjn/image/upload/" + image;
        product.setName(name);
        product.setSku(sku);
        product.setDescription(description);
        product.setUnit_price(Double.parseDouble(price));
        product.setBrand(idBrand);
        product.setCategory(idCategory);
        product.setImage(url);
        return product;
    }
}