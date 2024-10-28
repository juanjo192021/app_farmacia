package com.app.farmacia_fameza;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductAddDTO;
import com.app.farmacia_fameza.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addProductFragment extends Fragment{

    int id;
    Context context;
    bBrand bBrand;
    bProduct bProduct;
    bCategory bCategory;
    private Button buttonAddProduct;
    private EditText nameProduct, skuProduct, descriptionProduct, priceProduct, imageProduct;
    private Spinner brandProduct, categoryProduct;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addProductFragment newInstance(String param1, String param2) {
        addProductFragment fragment = new addProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        nameProduct = view.findViewById(R.id.txtRegisterNombre);
        skuProduct = view.findViewById(R.id.txtRegisterSKU);
        descriptionProduct = view.findViewById(R.id.txtRegisterDescription);
        priceProduct = view.findViewById(R.id.txtRegisterPrice);
        imageProduct = view.findViewById(R.id.txtRegisterImage);
        brandProduct = view.findViewById(R.id.spinnerRegisterBrand);
        setupBrandSpinner(brandProduct);

        categoryProduct = view.findViewById(R.id.spinnerRegisterCategory);
        setupCategorySpinner(categoryProduct);

        buttonAddProduct = view.findViewById(R.id.btnRegisterProduct);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Colocar el proceso de agregar
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    /*private void saveProduct(){
        ProductAddDTO product = completeDataProduct();
        if(id == 0){
            bProduct.insertProduct(product);
            Toast.makeText(context, "Guardar OK", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Actualizado OK", Toast.LENGTH_LONG).show();
        }
    }

    private ProductAddDTO completeDataProduct(){
        ProductAddDTO product = new ProductAddDTO();
        String n = nameProduct.getText().toString();
        String s = skuProduct.getText().toString();
        String d = descriptionProduct.getText().toString();
        String p = priceProduct.getText().toString();
        String b = brandProduct.getSelectedItem().toString();
        String c = categoryProduct.getSelectedItem().toString();
        String i = imageProduct.getText().toString();

        product.setName(n);
        product.setSku(s);
        product.setDescription(d);
        product.setUnit_price(Double.parseDouble(p));
        product.setBrand(b);
        product.setCategory(c);
        product.setImage(i);
        return product;
    }*/
}