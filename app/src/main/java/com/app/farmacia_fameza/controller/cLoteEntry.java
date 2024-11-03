package com.app.farmacia_fameza.controller;
import android.content.ContentValues;
import android.content.Context;
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

            // Insertar cada detalle en la tabla Product_Entry_Detail
            for (ProductEntryDetailDTO detail : productDetails) {
                ContentValues detailValues = new ContentValues();
                detailValues.put("entry_id", entryId);

                int idProduct = CProduct.getIDProductBySKU(detail.getSKU());
                detailValues.put("product_id", idProduct);

                detailValues.put("quantity", detail.getQuantity());
                detailValues.put("expiration_date", detail.getExpiration_date());
                detailValues.put("production_date", detail.getProduction_date());
                detailValues.put("alert_date", detail.getAlert_date());

                long detailResult = database.insert(TABLE_PRODUCT_ENTRY_DETAIL, null, detailValues);
                if (detailResult == -1) {
                    Log.e("Insert Entry Detail", "Error al insertar el detalle de la entrada para producto ID: " + idProduct);
                    return false;
                }

                // Actualizar el stock del producto
                CProduct.updateProductStock(idProduct, detail.getQuantity());
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

}
