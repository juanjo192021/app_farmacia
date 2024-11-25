package com.app.farmacia_fameza;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.farmacia_fameza.dto.ProductInventoryDTO;
import com.app.farmacia_fameza.dto.ProductUpdateDTO;
import com.app.farmacia_fameza.view.GetPDF;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.app.farmacia_fameza.business.bProduct;

import org.bouncycastle.asn1.sec.SECNamedCurves;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KardexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KardexFragment extends Fragment {

    Spinner mes;
    Button generarReporte;
    ImageView buscarSKU;
    TextView searchProduct, setProduct;
    com.app.farmacia_fameza.business.bProduct bProduct;
    // TODO: Rename and change types and number of parameters
    public static KardexFragment newInstance(String param1, String param2) {
        KardexFragment fragment = new KardexFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bProduct = new bProduct(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kardex, container, false);
        mes = view.findViewById(R.id.spinnerKardex);
        setupStatusMonth(mes);
        searchProduct = view.findViewById(R.id.txtskuKardex);
        setProduct = view.findViewById(R.id.txtsetProductName);
        buscarSKU = view.findViewById(R.id.btnBuscarskuKardex);
        buscarSKU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sku = searchProduct.getText().toString();
                setProductName(sku);
            }
        });
        generarReporte = view.findViewById(R.id.btnGenerarReporteKardex);
        generarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdfMonth();
            }
        });
        generarReporte.setEnabled(false);
        return view;
    }

    private void setProductName(String sku){
        String buscar = bProduct.searchProductName(sku);
        String error = "No Registrado";
        if(buscar == null){
            generarReporte.setEnabled(false);
            Toast.makeText(requireActivity(), "El sku ingresado no existe", Toast.LENGTH_LONG).show();
            setProduct.setText(error);
        }
        else{
            generarReporte.setEnabled(true);
            setProduct.setText(buscar);
            Toast.makeText(requireActivity(), "SKU encontrado", Toast.LENGTH_LONG).show();
        }
    }

    private void setupStatusMonth(Spinner spinner) {
        List<String> statusOptions = Arrays.asList(
                "enero",
                "febrero",
                "marzo",
                "abril",
                "mayo",
                "junio",
                "julio",
                "agosto",
                "septiembre",
                "octubre",
                "noviembre",
                "diciembre"
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void generatePdfMonth(){
        String month = mes.getSelectedItem().toString();
        String sku = searchProduct.getText().toString();
        Integer idProduct = bProduct.searchIdProduct(sku);
        boolean band = bProduct.validateRegisterKardex(idProduct);
        if(band){
            List<ProductInventoryDTO> productInventoryDTOList = bProduct.completeTableKardexFilterMonthSKU(month,idProduct);
            if (productInventoryDTOList == null || productInventoryDTOList.isEmpty()) {
                Toast.makeText(requireActivity(), "No hay registros de entrada del producto en el mes seleccionado.", Toast.LENGTH_LONG).show();
            } else {
                int randomNumber = new Random().nextInt(9000) + 1000;
                String fileName = "FarmaciaFameza"+ "_" + randomNumber + ".pdf";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    ContentResolver resolver = requireActivity().getContentResolver();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/FarmaciaFameza");

                    Uri pdfUri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues);

                    try {
                        if (pdfUri != null) {
                            List<Cell[]> tableRows = new ArrayList<>();
                            for (ProductInventoryDTO product : productInventoryDTOList) {
                                tableRows.add(new Cell[]{
                                        new Cell().add(new Paragraph(product.getSku())),
                                        new Cell().add(new Paragraph(product.getNameProduct())),
                                        new Cell().add(new Paragraph(String.valueOf(product.getPrecio()))),
                                        new Cell().add(new Paragraph(product.getFecha())),
                                        new Cell().add(new Paragraph(product.getDetalle())),
                                        new Cell().add(new Paragraph(String.valueOf(product.getEntrada()))),
                                        new Cell().add(new Paragraph(String.valueOf(product.getSalida()))),
                                        new Cell().add(new Paragraph(String.valueOf(product.getTotal()))),
                                        new Cell().add(new Paragraph(String.valueOf(product.getSaldo())))
                                });
                            }
                            OutputStream outputStream = resolver.openOutputStream(pdfUri);
                            if (outputStream != null) {
                                GetPDF.createPDF(requireActivity(), fileName, tableRows);
                                outputStream.close();
                                Toast.makeText(requireActivity(), "PDF guardado correctamente.", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(requireActivity(), "Error al generar el PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        else {
            Toast.makeText(requireActivity(), "No hay ninguna entrada registrada con ese producto", Toast.LENGTH_LONG).show();
        }
    }

    /*private void generatePdf() {
        List<ProductInventoryDTO> productInventoryDTOList = bProduct.completeTableKardex();

        int randomNumber = new Random().nextInt(9000) + 1000;
        String fileName = "FarmaciaFameza"+ "_" + randomNumber + ".pdf";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            ContentResolver resolver = requireActivity().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/FarmaciaFameza");

            Uri pdfUri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues);

            try {
                if (pdfUri != null) {
                    List<Cell[]> tableRows = new ArrayList<>();
                    for (ProductInventoryDTO product : productInventoryDTOList) {
                        tableRows.add(new Cell[]{
                                new Cell().add(new Paragraph(product.getSku())),
                                new Cell().add(new Paragraph(product.getNameProduct())),
                                new Cell().add(new Paragraph(product.getFecha())),
                                new Cell().add(new Paragraph(product.getDetalle())),
                                new Cell().add(new Paragraph(String.valueOf(product.getEntrada()))),
                                new Cell().add(new Paragraph(String.valueOf(product.getSalida()))),
                                new Cell().add(new Paragraph(String.valueOf(product.getSaldo())))
                        });
                    }
                    OutputStream outputStream = resolver.openOutputStream(pdfUri);
                    if (outputStream != null) {
                        GetPDF.createPDF(requireActivity(), fileName, tableRows);
                        outputStream.close();
                        Toast.makeText(requireActivity(), "PDF guardado correctamente.", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(requireActivity(), "Error al generar el PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }*/
}