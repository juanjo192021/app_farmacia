package com.app.farmacia_fameza.controller;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.farmacia_fameza.dao.conexion;
import com.app.farmacia_fameza.dto.ProductEntryDetailDTO;

import java.util.List;

public class cLoteEntry extends conexion{

    Context context;
    cSupplier CSupplier;
    cProduct CProduct;

    public cLoteEntry(@Nullable Context context) {
        super(context);
        this.context = context;
        CSupplier = new cSupplier(context);
        CProduct = new cProduct(context);
    }

    public boolean insertProductEntryWithDetails(String numberEntry, String dateEntry, String supplierName, List<ProductEntryDetailDTO> productDetails) {

        int idSupplier = CSupplier.getIDSupplier(supplierName);

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues entryValues = new ContentValues();
        String transaction_type = "entry";
        String detail_entry = "compra";
        try {
            // Insertar en la tabla Product_Entry
            entryValues.put("number_entry", numberEntry);
            entryValues.put("date_entry", dateEntry);
            entryValues.put("supplier_id", idSupplier);

            // Insertar la entrada y obtener el ID
            long entryId = database.insert(TABLE_PRODUCT_ENTRY, null, entryValues);
            if (entryId == -1) {
                Log.e("Insert Entry", "Error al insertar la entrada de producto");
                return false;
            }
            ContentValues transactionValues = new ContentValues();
            transactionValues.put("transaction_type", transaction_type);
            transactionValues.put("detail", detail_entry);
            transactionValues.put("transaction_id", entryId);
            database.insert(TABLE_INVENTORY_TRANSACTION, null, transactionValues);

            ContentValues detailValues = new ContentValues();
            // Insertar cada detalle en la tabla Product_Entry_Detail
            for (ProductEntryDetailDTO detail : productDetails) {
                detailValues.put("entry_id", entryId);

                detailValues.put("product_id", detail.getId());

                detailValues.put("quantity", detail.getQuantity());
                detailValues.put("price_history",detail.getPriceHistory());
                detailValues.put("expiration_date", detail.getExpiration_date());
                detailValues.put("production_date", detail.getProduction_date());
                detailValues.put("alert_date", detail.getAlert_date());

                long detailResult = database.insert(TABLE_PRODUCT_ENTRY_DETAIL, null, detailValues);
                if (detailResult == -1) {
                    Log.e("Insert Entry Detail", "Error al insertar el detalle de la entrada para producto ID: " + detail.getId());
                    return false;
                }

                // Actualizar el stock del producto - isAddition = true
                CProduct.updateProductStock(detail.getId(), detail.getQuantity(), true);
            }

            Log.d("Insert Entry With Details", "Entrada y detalles insertados exitosamente");
            return true;

        } catch (Exception e) {
            Log.e("Insert Entry Error", "Error al insertar entrada de producto con detalles: " + e.getMessage());
            return false;
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public String generateNextEntryCode() {
        SQLiteDatabase database = this.getReadableDatabase();
        String lastCodeQuery = "SELECT number_entry FROM " + TABLE_PRODUCT_ENTRY +
                " ORDER BY number_entry DESC LIMIT 1"; // Obtener el último código insertado

        Cursor cursor = database.rawQuery(lastCodeQuery, null);
        String newCode = "ENT001"; // Valor predeterminado en caso de no encontrar ningún código

        if (cursor != null && cursor.moveToFirst()) {
            String lastCode = cursor.getString(0);  // Obtener el último código
            cursor.close();

            // Extraer la parte numérica del último código
            String numberPart = lastCode.replaceAll("[^0-9]", ""); // Eliminar todas las letras
            int nextNumber = Integer.parseInt(numberPart) + 1; // Incrementar el número

            // Formatear el siguiente código con el prefijo y el número incrementado
            newCode = "ENT" + String.format("%03d", nextNumber); // Asegurar que el número tenga 3 dígitos
        }

        return newCode;
    }

}
