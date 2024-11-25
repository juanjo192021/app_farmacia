package com.app.farmacia_fameza.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.app.farmacia_fameza.dao.conexion;
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;


import java.util.ArrayList;
import java.util.List;

public class cBrand extends conexion {

    Context context;

    public cBrand(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("Range")
    public List<ItemListDTO> getBrands() {
        List<ItemListDTO> brandList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT b.id, b.name, b.status, COUNT(p.id) AS product_count " +
                    "FROM " + TABLE_BRAND + " b " +
                    "LEFT JOIN " + TABLE_PRODUCT + " p ON b.id = p.brand_id " +
                    "GROUP BY b.id, b.name, b.status " +
                    "ORDER BY b.name;";

            cursor = database.rawQuery(query, null);

            // Recorrer el cursor y agregar marcas a la lista
            if (cursor.moveToFirst()) {
                do {
                    ItemListDTO itemListDTO = new ItemListDTO();
                    itemListDTO.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    itemListDTO.setName(cursor.getString(cursor.getColumnIndex("name")));

                    // Convertir status de 1/0 a "Activo"/"Inactivo"
                    int statusValue = cursor.getInt(cursor.getColumnIndex("status"));
                    String status = (statusValue == 1) ? "Activo" : "Inactivo";
                    itemListDTO.setStatus(status);  // Usa este campo en lugar de `status`

                    itemListDTO.setCount_Product(cursor.getInt(cursor.getColumnIndex("product_count")));

                    brandList.add(itemListDTO);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Get Brands Error", "Error al obtener marcas: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return brandList;
    }

    public boolean addBrand(String name, int status) {
        SQLiteDatabase database = this.getWritableDatabase();
        boolean success = false;

        try {
            // Crear un ContentValues con los valores de la nueva categoría
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("status", status);

            // Insertar la nueva categoría en la tabla de categorías
            long result = database.insert(TABLE_BRAND, null, values);

            // Si el resultado es -1, significa que la inserción falló
            success = result != -1;

        } catch (Exception e) {
            Log.e("Add Brand Error", "Error al agregar marca: " + e.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return success;
    }

    public boolean editBrand(int id, String name, int status) {
        SQLiteDatabase database = this.getWritableDatabase();
        boolean success = false;

        try {
            // Crear un ContentValues con los nuevos valores para la categoría
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("status", status);

            // Actualizar la categoría en la tabla usando el id como criterio
            int result = database.update(TABLE_BRAND, values, "id = ?", new String[]{String.valueOf(id)});

            // Si el resultado es mayor a 0, significa que la actualización fue exitosa
            success = result > 0;

        } catch (Exception e) {
            Log.e("Edit Brand Error", "Error al editar marca: " + e.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return success;
    }


    @SuppressLint("Range")
    public List<String> getBrandsName() {
        List<String> brandList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT name FROM " + TABLE_BRAND;
            cursor = database.rawQuery(query, null);

            // Recorrer el cursor y agregar marcas a la lista
            if (cursor.moveToFirst()) {
                do {
                    String nameProduct = cursor.getString(cursor.getColumnIndex("name"));
                    brandList.add(nameProduct);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Get Brands Error", "Error al obtener marcas: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return brandList;
    }

    public Double searchPriceProduct(Integer productId){
        double price = 0.0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_HISTORY_PRICE_PRODUCT +
                    " WHERE product_id = ? " +
                    " ORDER BY date_register DESC " +
                    " LIMIT 1";
            cursor = database.rawQuery(query, new String[]{String.valueOf(productId)});
            if (cursor.moveToFirst()) {
                price = cursor.getDouble(2);
            }
        }catch (Exception e){
            Log.e("Get Search SKU Error", "Error al obtener SKU del producto: " + e.getMessage());
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return price;
    }

    @SuppressLint("Range")
    public List<ProductListDTO> getProductsByBrand(int brandId) {
        List<ProductListDTO> productList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT id, name, stock " +
                    "FROM " + TABLE_PRODUCT + " p " +
                    "WHERE p.brand_id = ? " +   // Correctamente dentro de la cadena
                    "ORDER BY name ASC";
            cursor = database.rawQuery(query, new String[]{String.valueOf(brandId)});

            // Recorrer el cursor y agregar productos a la lista
            if (cursor.moveToFirst()) {
                do {
                    ProductListDTO product = new ProductListDTO();
                    product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    product.setName(cursor.getString(cursor.getColumnIndex("name")));
                    Double price = searchPriceProduct(cursor.getInt(cursor.getColumnIndex("id")));
                    product.setUnit_price(price);
                    product.setStock_actual(cursor.getInt(cursor.getColumnIndex("stock")));

                    productList.add(product);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Get Products Error", "Error al obtener productos: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return productList;
    }

    public Integer getIDBrand(String nameBrand) {
        Integer brandId = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT id FROM " + TABLE_BRAND + " WHERE name = ?";
            cursor = database.rawQuery(query, new String[]{nameBrand});

            if (cursor.moveToFirst()) {
                brandId = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e("Get Brand ID Error", "Error al obtener ID del brand: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return brandId;
    }

}
