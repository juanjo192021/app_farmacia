package com.app.farmacia_fameza.view;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.app.farmacia_fameza.dto.ProductKardexDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import java.util.List;
import com.itextpdf.layout.element.Cell;

public class GetPDF {
    public static void createPDF(Context context, String fileName, List<Cell[]> rows) {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;

        try {
            File file = new File(filePath);
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Reporte de Inventario").setBold().setFontSize(16));

            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 3, 2, 3, 3, 3, 3, 3, 3}))
                    .useAllAvailableWidth();

            // Agregar encabezados
            String[] headers = {"Lote", "Ingreso", "Aviso", "Vence", "Proveedor", "Entrada", "Salida", "Actual"};
            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header).setBold()));
            }

            // Procesar cada fila de datos
            for (Cell[] row : rows) {
                int totalColumns = 0;
                for (Cell cell : row) {
                    table.addCell(cell);
                    totalColumns++;

                    // Si alcanzamos 8 columnas, hacer un salto de fila
                    if (totalColumns == 8) {
                        totalColumns = 0; // Reiniciar el conteo para la nueva fila
                    }
                }

                // Si la última fila tiene menos de 8 columnas, rellenar con celdas vacías
                while (totalColumns > 0 && totalColumns < 8) {
                    table.addCell(new Cell().add(new Paragraph("")));
                    totalColumns++;
                }
            }

            document.add(table);
            document.close();

            Toast.makeText(context, "PDF creado exitosamente en: " + filePath, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error al crear el PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
