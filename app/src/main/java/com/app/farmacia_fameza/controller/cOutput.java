package com.app.farmacia_fameza.controller;
import android.content.ContentValues;
import android.content.Context;
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

        try {
            // Insertar en la tabla Product_Entry
            entryValues.put("number_entry", numberOuput);
            entryValues.put("date_entry", dateOutput);
            entryValues.put("supplier_id", userName);

            // Insertar la entrada y obtener el ID
            long outputId = database.insert(TABLE_PRODUCT_OUTPUT, null, entryValues);
            if (outputId == -1) {
                Log.e("Insert Entry", "Error al insertar la salida de producto");
                return false;
            }

            // Insertar cada detalle en la tabla Product_Entry_Detail
            for (ProductOutputDetailDTO detail : productDetails) {
                ContentValues detailValues = new ContentValues();
                detailValues.put("output_id", outputId);

                int idProduct = CProduct.getIDProductBySKU(detail.getSKU());
                detailValues.put("product_id", idProduct);
                detailValues.put("quantity", detail.getQuantity());

                long detailResult = database.insert(TABLE_PRODUCT_ENTRY_DETAIL, null, detailValues);
                if (detailResult == -1) {
                    Log.e("Insert Output Detail", "Error al insertar el detalle de la salida para producto ID: " + idProduct);
                    return false;
                }

                // Actualizar el stock del producto - isAddition = false
                CProduct.updateProductStock(idProduct, detail.getQuantity(), false);
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
}
