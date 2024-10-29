package com.app.farmacia_fameza;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.farmacia_fameza.business.bCategory;

public class CategoryCrudFragment extends Fragment {

    private bCategory Bcategory;
    private static final String ARG_ID = "idCategory";
    private static final String ARG_NAME = "nameCategory";
    private static final String ARG_STATUS = "statusCategory";
    private static final String ARG_QUANTITY = "quantityCategory";
    private static final String ARG_IS_EDIT_FRAME = "isEditFrame";

    private int idCategory;
    private String nameCategory;
    private String statusCategory;
    private String quantityCategory;
    private boolean isEditFrame;

    private EditText nameCategoryTV, statusCategoryTV;
    private TextView quantityCategoryTV;
    private Button buttonAddCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCategory = getArguments().getInt(ARG_ID);
            nameCategory = getArguments().getString(ARG_NAME);
            statusCategory = getArguments().getString(ARG_STATUS);
            quantityCategory = getArguments().getString(ARG_QUANTITY);
            isEditFrame = getArguments().getBoolean(ARG_IS_EDIT_FRAME, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_crud, container, false);

        Bcategory = new bCategory(getContext());

        buttonAddCategory = view.findViewById(R.id.btnAddCategory);
        nameCategoryTV = view.findViewById(R.id.nameTextViewC);
        statusCategoryTV = view.findViewById(R.id.statusTextViewC);
        quantityCategoryTV = view.findViewById(R.id.quantityTextViewC);

        // Configuración de vistas en función del modo (agregar o editar)
        if (isEditFrame) {
            setupEditMode();
        } else {
            setupAddMode();
        }

        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCategorySave();
            }
        });

        return view;
    }

    private void setupEditMode() {
        if (nameCategory != null) {
            nameCategoryTV.setText(nameCategory);
        }
        if (statusCategory != null) {
            statusCategoryTV.setText(statusCategory);
        }
        if (quantityCategory != null) {
            quantityCategoryTV.setText(quantityCategory);
        }

        buttonAddCategory.setText("Editar");

        // Configura el enfoque y muestra el teclado
        nameCategoryTV.requestFocus();
        nameCategoryTV.setFocusableInTouchMode(true);

        showKeyboard(nameCategoryTV);
    }

    private void setupAddMode() {
        buttonAddCategory.setText("Agregar");

        // Enfocar y abrir el teclado para el campo de nombre en modo agregar
        nameCategoryTV.requestFocus();
        showKeyboard(nameCategoryTV);
    }

    private void handleCategorySave() {
        String Mode = (String) buttonAddCategory.getText();
        String name = nameCategoryTV.getText().toString().trim();
        String statusText = statusCategoryTV.getText().toString().trim();
        int status = statusText.equals("Inactivo") ? 0 : 1;
        if(Mode.equals("Agregar")){
            boolean isAdded = Bcategory.addCategory(name, status);

            if (isAdded) {
                Toast.makeText(getContext(), "Categoría agregada con éxito", Toast.LENGTH_SHORT).show();
                nameCategoryTV.setText("");
                statusCategoryTV.setText("");
            } else {
                Toast.makeText(getContext(), "Error al agregar la categoría", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            boolean isEdited = Bcategory.editCategory(idCategory, name, status);

            if (isEdited) {
                Toast.makeText(getContext(), "Categoría editada con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al editar la categoría", Toast.LENGTH_SHORT).show();
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
