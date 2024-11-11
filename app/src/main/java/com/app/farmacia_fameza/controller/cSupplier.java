package com.app.farmacia_fameza.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.farmacia_fameza.dao.conexion;

import java.util.ArrayList;
import java.util.List;

public class cSupplier extends conexion{

    Context context;

    public cSupplier(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("Range")
    public List<String> getAllSupplierNames() {
        List<String> supplierNames = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Consulta para obtener todos los nombres de los proveedores
            String query = "SELECT name FROM Supplier"; // Asegúrate de que el nombre de la tabla y la columna sean correctos
            cursor = database.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Obtener el nombre del proveedor
                    String supplierName = cursor.getString(cursor.getColumnIndex("name"));
                    supplierNames.add(supplierName); // Agregar a la lista
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("Get Suppliers Error", "Error al obtener nombres de proveedores: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close(); // Cerrar el cursor
            }
            if (database != null && database.isOpen()) {
                database.close(); // Cerrar la base de datos
            }
        }
        return supplierNames; // Devolver la lista de nombres de proveedores
    }

    public Integer getIDSupplier(String nameSupplier){
        Integer supplierId = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT id FROM " + TABLE_SUPPLIER + " WHERE name = ?";
            cursor = database.rawQuery(query, new String[]{nameSupplier});

            if (cursor.moveToFirst()) {
                supplierId = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e("Get Supplier ID Error", "Error al obtener ID del proveedor: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return supplierId;
    }
}
