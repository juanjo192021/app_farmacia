package com.app.farmacia_fameza.ui.output;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.business.bOutput;
import com.app.farmacia_fameza.databinding.FragmentOutputBinding;
import com.app.farmacia_fameza.dto.ProductOutputDetailDTO;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class OutputFragment extends Fragment {

    FragmentOutputBinding binding;

    bOutput BOutput;

    Button btnAddProductOutput, btnSaveOutput;

    TableLayout tblInsertProductsOutput;

    EditText txtNumberOutput, txtDateOutput;

    private final ArrayList<ProductOutputDetailDTO> productOutputDetailList = new ArrayList<>(); // Lista de productos

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOutputBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        BOutput = new bOutput(getContext());

        btnAddProductOutput = root.findViewById(R.id.btn_AgregarProductoSalida);
        btnSaveOutput = root.findViewById(R.id.btn_AgregarOutput);

        tblInsertProductsOutput = root.findViewById(R.id.tblInsertProductsOutput); // Referencia a TableLayout
        tblInsertProductsOutput.setGravity(View.TEXT_ALIGNMENT_CENTER);

        txtNumberOutput = root.findViewById(R.id.txt_codigoSalida);
        txtDateOutput = root.findViewById(R.id.txt_FechaSalida);

        createHeaderToTable();

        btnAddProductOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext())
                        .inflate(
                                R.layout.modal_detailproductoutput,
                                (LinearLayout) getView().findViewById(R.id.bottomDetailProductOutputModal)
                        );

                bottomSheetView.findViewById(R.id.btn_AgregarProdOutput).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText productSKU = bottomSheetView.findViewById(R.id.editTextSkuProdOutput);
                        EditText quantity = bottomSheetView.findViewById(R.id.editTextCantidadProdOutput);

                        addProductToTable(
                                productSKU.getText().toString(),
                                Integer.parseInt(quantity.getText().toString()));

                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        btnSaveOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = BOutput.insertProductOutputWithDetails(txtNumberOutput.getText().toString(),
                        txtDateOutput.getText().toString(),
                        "Usuario1",
                        productOutputDetailList);
                // Verificar el resultado de la inserción
                if (isSuccess) {
                    // Mostrar un mensaje de éxito
                    Toast.makeText(getContext(), "Salida de productos guardado con éxito", Toast.LENGTH_SHORT).show();
                    // Aquí puedes agregar lógica adicional si es necesario, como limpiar campos o actualizar la interfaz
                    // Limpiar tabla y campos de entrada
                    clearTable();
                    clearFields();
                } else {
                    // Mostrar un mensaje de error
                    Toast.makeText(getContext(), "Error al guardar la Salida de Productos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void addProductToTable(String productSKU, int quantity) {
        // Crear un nuevo producto y añadir a la lista
        ProductOutputDetailDTO product = new ProductOutputDetailDTO(productSKU, quantity);
        productOutputDetailList.add(product);

        // Crear una nueva fila
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        // Añadir TextViews
        newRow.addView(createTextView(productSKU));
        newRow.addView(createTextView(String.valueOf(quantity)));

        // Crear y añadir botón de eliminar
        Button btnDelete = new Button(getContext());
        btnDelete.setText("Eliminar");
        btnDelete.setPadding(16, 16, 16, 16);
        btnDelete.setOnClickListener(v -> {
            tblInsertProductsOutput.removeView(newRow);
            productOutputDetailList.remove(product);
        });
        newRow.addView(btnDelete);

        // Agregar la nueva fila al TableLayout
        tblInsertProductsOutput.addView(newRow);
    }

    // Método para limpiar la tabla (excepto la fila de encabezado)
    private void clearTable() {
        int childCount = tblInsertProductsOutput.getChildCount();
        if (childCount > 1) { // Mantener la fila de encabezado
            tblInsertProductsOutput.removeViews(1, childCount - 1);
        }
        productOutputDetailList.clear(); // Limpiar la lista de productos
    }

    // Método para limpiar los campos de entrada
    private void clearFields() {
        txtNumberOutput.setText("");
        txtDateOutput.setText("");
    }

    // Método auxiliar para crear TextView
    private TextView createTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(16, 16, 16, 16);
        return textView;
    }

    private void createHeaderToTable(){
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        String[] headers = {"Sku", "Cantidad", "Acciones"};
        for (String header : headers) {
            TextView textView = new TextView(getContext());
            textView.setText(header);
            textView.setPadding(16, 16, 16, 16);
            newRow.addView(textView);
        }

        tblInsertProductsOutput.addView(newRow);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}