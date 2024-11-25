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
import com.app.farmacia_fameza.business.bProduct;
import com.app.farmacia_fameza.databinding.FragmentOutputBinding;
import com.app.farmacia_fameza.dto.ProductOutputDetailDTO;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OutputFragment extends Fragment {

    FragmentOutputBinding binding;

    bOutput BOutput;
    bProduct BProduct;

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
        BProduct = new bProduct(getContext());

        btnAddProductOutput = root.findViewById(R.id.btn_AgregarProductoSalida);
        btnSaveOutput = root.findViewById(R.id.btn_AgregarOutput);

        tblInsertProductsOutput = root.findViewById(R.id.tblInsertProductsOutput); // Referencia a TableLayout
        tblInsertProductsOutput.setGravity(View.TEXT_ALIGNMENT_CENTER);

        txtNumberOutput = root.findViewById(R.id.txt_codigoSalida);
        txtDateOutput = root.findViewById(R.id.txt_FechaSalida);

        generateDataOutput();

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

                        Integer idPriceHistory = BProduct.searchIdProduct(productSKU.getText().toString());
                        double priceHistory = Math.round(BProduct.searchPriceProductByID(idPriceHistory) * 1.15 * 10.0) / 10.0;
                        addProductToTable(
                                productSKU.getText().toString(),
                                Integer.parseInt(quantity.getText().toString()),
                                priceHistory);

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
                    generateDataOutput();
                } else {
                    // Mostrar un mensaje de error
                    Toast.makeText(getContext(), "Error al guardar la Salida de Productos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void generateDataOutput(){
        txtNumberOutput.setText(BOutput.generateNextOutputCode());
        txtDateOutput.setText(getCurrentDateString());
    }

    public String getCurrentDateString() {
        // Obtén la fecha actual
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Define el formato de la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }

    private void addProductToTable(String productSKU, int quantity, Double price) {
        int ProductId = BProduct.searchIdProduct(productSKU);

        // Crear un nuevo producto y añadir a la lista
        ProductOutputDetailDTO product = new ProductOutputDetailDTO(ProductId, productSKU, quantity, price);
        productOutputDetailList.add(product);

        // Crear una nueva fila
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        // Añadir TextViews
        newRow.addView(createTextView(productSKU));
        newRow.addView(createTextView(String.valueOf(quantity)));
        newRow.addView(createTextView(String.valueOf(price)));


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

        String[] headers = {"Sku", "Cantidad", "Precio", "Acciones"};
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