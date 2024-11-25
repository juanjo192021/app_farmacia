package com.app.farmacia_fameza.controller;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.farmacia_fameza.dao.conexion;
import com.app.farmacia_fameza.dto.ProductOutputDetailDTO;

import java.util.List;

public class cOutput extends conexion {

    Context context;
    cProduct CProduct;

    public cOutput(@Nullable Context context) {
        super(context);
        this.context = context;
        this.CProduct = new cProduct(context);
    }

    public boolean insertProductOutputWithDetails(String numberOuput, String dateOutput, String userName, List<ProductOutputDetailDTO> productDetails) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues entryValues = new ContentValues();
        String transaction_type = "output";
        String detail_output = "venta";
        try {
            // Insertar en la tabla Product_Entry
            entryValues.put("output_code", numberOuput);
            entryValues.put("output_date", dateOutput);
            entryValues.put("user_id", 1);

            // Insertar la entrada y obtener el ID
            long outputId = database.insert(TABLE_PRODUCT_OUTPUT, null, entryValues);
            if (outputId == -1) {
                Log.e("Insert Output", "Error al insertar la salida de producto");
                return false;
            }

            ContentValues transactionValues = new ContentValues();
            transactionValues.put("transaction_type", transaction_type);
            transactionValues.put("detail", detail_output);
            transactionValues.put("transaction_id", outputId);
            database.insert(TABLE_INVENTORY_TRANSACTION, null, transactionValues);

            // Insertar cada detalle en la tabla Product_Entry_Detail
            for (ProductOutputDetailDTO detail : productDetails) {
                ContentValues detailValues = new ContentValues();
                detailValues.put("output_id", outputId);
                detailValues.put("product_id", detail.getId());
                detailValues.put("quantity", detail.getQuantity());
                detailValues.put("price_history", detail.getPriceHistory());

                long detailResult = database.insert(TABLE_PRODUCT_OUTPUT_DETAIL, null, detailValues);
                if (detailResult == -1) {
                    Log.e("Insert Output Detail", "Error al insertar el detalle de la salida para producto ID: " + detail.getId());
                    return false;
                }

                // Actualizar el stock del producto - isAddition = false
                CProduct.updateProductStock(detail.getId(), detail.getQuantity(), false);
            }

            Log.d("Insert Output With Details", "Salida detalles insertados exitosamente");
            return true;

        } catch (Exception e) {
            Log.e("Insert Output Error", "Error al insertar SALIDA de producto con detalles: " + e.getMessage());
            return false;
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public String generateNextOutputCode() {
        SQLiteDatabase database = this.getReadableDatabase();
        String lastCodeQuery = "SELECT output_code FROM " + TABLE_PRODUCT_OUTPUT +
                " ORDER BY output_code DESC LIMIT 1"; // Obtener el último código insertado

        Cursor cursor = database.rawQuery(lastCodeQuery, null);
        String newCode = "OUT001"; // Valor predeterminado en caso de no encontrar ningún código

        if (cursor != null && cursor.moveToFirst()) {
            String lastCode = cursor.getString(0);  // Obtener el último código
            cursor.close();

            // Extraer la parte numérica del último código
            String numberPart = lastCode.replaceAll("[^0-9]", ""); // Eliminar todas las letras
            int nextNumber = Integer.parseInt(numberPart) + 1; // Incrementar el número

            // Formatear el siguiente código con el prefijo y el número incrementado
            newCode = "OUT" + String.format("%03d", nextNumber); // Asegurar que el número tenga 3 dígitos
        }

        return newCode;
    }
}
