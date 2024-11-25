package com.app.farmacia_fameza.view.ui.brand;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.business.bBrand;

import java.util.Arrays;
import java.util.List;


public class BrandCrudFragment extends Fragment {

    private bBrand Bbrand;
    private static final String ARG_ID = "idBrand";
    private static final String ARG_NAME = "nameBrand";
    private static final String ARG_STATUS = "statusBrand";
    private static final String ARG_QUANTITY = "quantityBrand";
    private static final String ARG_IS_EDIT_FRAME = "isEditFrame";

    private int idBrand;
    private String nameBrand;
    private String statusBrand;
    private String quantityBrand;
    private boolean isEditFrame;

    private EditText nameBrandTV;
    private Spinner statusBrandSppiner;
    private TextView quantityBrandTV;
    private Button buttonAddBrand;

    public static BrandCrudFragment newInstance() {
        BrandCrudFragment fragment = new BrandCrudFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idBrand = getArguments().getInt(ARG_ID);
            nameBrand = getArguments().getString(ARG_NAME);
            statusBrand = getArguments().getString(ARG_STATUS);
            quantityBrand = getArguments().getString(ARG_QUANTITY);
            isEditFrame = getArguments().getBoolean(ARG_IS_EDIT_FRAME, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_brand_crud, container, false);

        Bbrand = new bBrand(getContext());

        buttonAddBrand = view.findViewById(R.id.btnAddBrand);
        nameBrandTV = view.findViewById(R.id.nameTextViewB);
        statusBrandSppiner = view.findViewById(R.id.statusSppinerB);
        setupStatusSpinner(statusBrandSppiner);
        quantityBrandTV = view.findViewById(R.id.quantityTextViewB);

        // Configuración de vistas en función del modo (agregar o editar)
        if (isEditFrame) {
            setupEditMode();
        } else {
            setupAddMode();
        }

        buttonAddBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBrandSave();
            }
        });

        return view;
    }

    public void setupStatusSpinner(Spinner spinner) {
        List<String> statusOptions = Arrays.asList("Activo", "Inactivo");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void setupEditMode() {
        if (nameBrand != null) {
            nameBrandTV.setText(nameBrand);
        }
        if (statusBrand != null) {
            if(statusBrand.equals("Inactivo")){
                statusBrandSppiner.setSelection(1);
            }
        }
        if (quantityBrand != null) {
            quantityBrandTV.setText(quantityBrand);
        }

        buttonAddBrand.setText("Editar");

        // Configura el enfoque y muestra el teclado
        nameBrandTV.requestFocus();
        nameBrandTV.setFocusableInTouchMode(true);

        showKeyboard(nameBrandTV);
    }

    private void setupAddMode() {
        buttonAddBrand.setText("Agregar");

        // Enfocar y abrir el teclado para el campo de nombre en modo agregar
        nameBrandTV.requestFocus();
        showKeyboard(nameBrandTV);
    }

    private void handleBrandSave() {
        int numberStatus;
        String Mode = (String) buttonAddBrand.getText();
        String name = nameBrandTV.getText().toString().trim();
        if(statusBrandSppiner.getSelectedItem().toString().equals("Activo")){
            numberStatus = 1;
        }else{
            numberStatus = 0;
        }
        if(Mode.equals("Agregar")){
            boolean isAdded = Bbrand.addBrand(name, numberStatus);

            if (isAdded) {
                Toast.makeText(getContext(), "Marca agregada con éxito", Toast.LENGTH_SHORT).show();
                nameBrandTV.setText("");
            } else {
                Toast.makeText(getContext(), "Error al agregar la marca", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            boolean isEdited = Bbrand.editBrand(idBrand, name, numberStatus);

            if (isEdited) {
                Toast.makeText(getContext(), "Marca editada con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al editar la marca", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}