package com.app.farmacia_fameza.ui.lote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.business.bLoteEntry;
import com.app.farmacia_fameza.business.bSupplier;
import com.app.farmacia_fameza.databinding.FragmentLoteBinding;
import com.app.farmacia_fameza.dto.ProductEntryDetailDTO;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LoteFragment extends Fragment {


    FragmentLoteBinding binding;
    Button btnAddProduct;

    Button btnSaveEntry; // Botón para guardar en la base de datos

    TableLayout tblInsertProducts;

    bLoteEntry BLoteEntry;
    bSupplier BSupplier;

    EditText txtNumberEntry, txtDateEntry;

    Spinner spinnerSupplier;

    private final ArrayList<ProductEntryDetailDTO> productEntryDetailList = new ArrayList<>(); // Lista de productos

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Inicializar BloteEntry
        BLoteEntry = new bLoteEntry(getContext());
        BSupplier = new bSupplier(getContext());

        btnAddProduct = root.findViewById(R.id.btn_AgregarLote);
        btnSaveEntry = root.findViewById(R.id.btn_AgregarEntryLote);

        tblInsertProducts = root.findViewById(R.id.tblInsertProducts); // Referencia a TableLayout
        tblInsertProducts.setGravity(View.TEXT_ALIGNMENT_CENTER);

        txtNumberEntry = root.findViewById(R.id.txt_codigoLote);
        txtDateEntry = root.findViewById(R.id.txt_FechaLote);
        spinnerSupplier = root.findViewById(R.id.spinner_ProveedorLote);
        setupSupplierSpinner(spinnerSupplier);

        createHeaderToTable();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext())
                        .inflate(
                                R.layout.modal_detailproduct,
                                (LinearLayout) getView().findViewById(R.id.bottomDetailProductModal)
                        );

                bottomSheetView.findViewById(R.id.btn_AgregarProdLote).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText productSKU = bottomSheetView.findViewById(R.id.editTextSkuProdLote);
                        EditText quantity = bottomSheetView.findViewById(R.id.editTextCantidadProdLote);
                        EditText productionDate = bottomSheetView.findViewById(R.id.editTextFechaProduccionProdLote);
                        EditText expirationDate = bottomSheetView.findViewById(R.id.editTextFechaExpiracionProdLote);

                        // Obtener la fecha de expiración
                        String expirationDateString = expirationDate.getText().toString();
                        String alertDateString = calculateAlertDate(expirationDateString);

                        addProductToTable(
                                productSKU.getText().toString(),
                                Integer.parseInt(quantity.getText().toString()),
                                expirationDate.getText().toString(),
                                productionDate.getText().toString(),
                                alertDateString);

                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = BLoteEntry.insertProductEntryWithDetails(txtNumberEntry.getText().toString(),
                                        txtDateEntry.getText().toString(),
                                        spinnerSupplier.getSelectedItem().toString(),
                                        productEntryDetailList);
                // Verificar el resultado de la inserción
                if (isSuccess) {
                    // Mostrar un mensaje de éxito
                    Toast.makeText(getContext(), "Entrada del Lote guardado con éxito", Toast.LENGTH_SHORT).show();
                    // Aquí puedes agregar lógica adicional si es necesario, como limpiar campos o actualizar la interfaz
                    // Limpiar tabla y campos de entrada
                    clearTable();
                    clearFields();
                } else {
                    // Mostrar un mensaje de error
                    Toast.makeText(getContext(), "Error al guardar la Entrada del Lote", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    // Método para limpiar la tabla (excepto la fila de encabezado)
    private void clearTable() {
        int childCount = tblInsertProducts.getChildCount();
        if (childCount > 1) { // Mantener la fila de encabezado
            tblInsertProducts.removeViews(1, childCount - 1);
        }
        productEntryDetailList.clear(); // Limpiar la lista de productos
    }

    // Método para limpiar los campos de entrada
    private void clearFields() {
        txtNumberEntry.setText("");
        txtDateEntry.setText("");
        spinnerSupplier.setSelection(0); // Seleccionar el primer proveedor en el Spinner
    }

    private void createHeaderToTable(){
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        String[] headers = {"Sku", "Cantidad", "Fecha Expiracion", "Fecha Produccion", "Fecha Alerta", "Acciones"};
        for (String header : headers) {
            TextView textView = new TextView(getContext());
            textView.setText(header);
            textView.setPadding(16, 16, 16, 16);
            newRow.addView(textView);
        }

        tblInsertProducts.addView(newRow);
    }

    private void addProductToTable(String productSKU, int quantity, String expirationDate, String productionDate, String alertDate) {
        // Crear un nuevo producto y añadir a la lista
        ProductEntryDetailDTO product = new ProductEntryDetailDTO(productSKU, quantity, expirationDate, productionDate, alertDate);
        productEntryDetailList.add(product);

        // Crear una nueva fila
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        // Añadir TextViews
        newRow.addView(createTextView(productSKU));
        newRow.addView(createTextView(String.valueOf(quantity)));
        newRow.addView(createTextView(expirationDate));
        newRow.addView(createTextView(productionDate));
        newRow.addView(createTextView(alertDate));

        // Crear y añadir botón de eliminar
        Button btnDelete = new Button(getContext());
        btnDelete.setText("Eliminar");
        btnDelete.setPadding(16, 16, 16, 16);
        btnDelete.setOnClickListener(v -> {
            tblInsertProducts.removeView(newRow);
            productEntryDetailList.remove(product);
        });
        newRow.addView(btnDelete);

        // Agregar la nueva fila al TableLayout
        tblInsertProducts.addView(newRow);
    }

    // Método auxiliar para crear TextView
    private TextView createTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(16, 16, 16, 16);
        return textView;
    }

    // Función para calcular la fecha de alerta
    private String calculateAlertDate(String expirationDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            // Establecer la fecha de expiración
            calendar.setTime(sdf.parse(expirationDateString));
            // Restar 2 meses
            calendar.add(Calendar.MONTH, -2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Formatear y devolver la fecha de alerta como String
        return sdf.format(calendar.getTime());
    }

    // Método para configurar el Spinner
    public void setupSupplierSpinner(Spinner spinner) {
        List<String> suppliers = BSupplier.getAllSupplierNames();

        // Crear un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, suppliers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Configurar el Spinner con el adaptador
        spinner.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}