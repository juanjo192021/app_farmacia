package com.app.farmacia_fameza.view;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.app.farmacia_fameza.dto.ProductKardexDTO;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.util.List;
import java.util.Map;

import com.itextpdf.layout.element.Cell;

public class GetPDF {
    public static void createPDF(Context context, String fileName, List<Cell[]> rows) {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;

        try {
            File file = new File(filePath);
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Título del reporte
            document.add(new Paragraph("Reporte de Inventario").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));

            // Crear tabla con ancho de columnas proporcionado
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 3, 2, 3, 2, 2, 2, 2}))
                    .useAllAvailableWidth();

            // Encabezados de la tabla
            String[] headers = {"SKU", "Nombre", "Precio","Fecha", "Detalle", "Entrada", "Salida", "Saldo"};
            for (String header : headers) {
                table.addHeaderCell(
                        new Cell()
                                .add(new Paragraph(header).setBold().setFontColor(ColorConstants.WHITE))
                                .setBackgroundColor(ColorConstants.BLUE)
                );
            }

            // Agregar filas de datos
            for (Cell[] row : rows) {
                for (Cell cell : row) {
                    table.addCell(cell);
                }
            }

            // Agregar tabla al documento
            document.add(table);
            document.close();

            Toast.makeText(context, "PDF creado exitosamente en: " + filePath, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error al crear el PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
