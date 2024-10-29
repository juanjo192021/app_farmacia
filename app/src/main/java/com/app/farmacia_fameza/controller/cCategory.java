package com.app.farmacia_fameza.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.farmacia_fameza.dao.conexion;
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;

import java.util.ArrayList;
import java.util.List;

public class cCategory extends conexion {

    Context context;

    public cCategory(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("Range")
    public List<ItemListDTO> getCategories() {
        List<ItemListDTO> categoryList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT c.id, c.name, c.status, COUNT(p.id) AS product_count " +
                    "FROM " + TABLE_CATEGORY + " c " +
                    "LEFT JOIN " + TABLE_PRODUCT + " p ON c.id = p.category_id " +
                    "GROUP BY c.id, c.name, c.status " +
                    "ORDER BY c.name;";

            cursor = database.rawQuery(query, null);

            // Recorrer el cursor y agregar categorias a la lista
            if (cursor.moveToFirst()) {
                do {
                    ItemListDTO itemListDTO = new ItemListDTO();
                    itemListDTO.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    itemListDTO.setName(cursor.getString(cursor.getColumnIndex("name")));
                    itemListDTO.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                    itemListDTO.setCount_Product(cursor.getInt(cursor.getColumnIndex("product_count")));

                    categoryList.add(itemListDTO);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Get Categories Error", "Error al obtener categorias: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return categoryList;
    }

    @SuppressLint("Range")
    public List<ProductListDTO> getProductsByCategory(int categoryId) {
        List<ProductListDTO> productList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT id, name, unit_price, stock " +
                    "FROM " + TABLE_PRODUCT + " p " +
                    "WHERE p.category_id = ? " +   // Correctamente dentro de la cadena
                    "ORDER BY name ASC";
            cursor = database.rawQuery(query, new String[]{String.valueOf(categoryId)});

            // Recorrer el cursor y agregar productos a la lista
            if (cursor.moveToFirst()) {
                do {
                    ProductListDTO product = new ProductListDTO();
                    product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    product.setName(cursor.getString(cursor.getColumnIndex("name")));
                    product.setUnit_price(cursor.getDouble(cursor.getColumnIndex("unit_price")));
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

    public boolean addCategory(String name, int status) {
        SQLiteDatabase database = this.getWritableDatabase();
        boolean success = false;

        try {
            // Crear un ContentValues con los valores de la nueva categoría
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("status", status);

            // Insertar la nueva categoría en la tabla de categorías
            long result = database.insert(TABLE_CATEGORY, null, values);

            // Si el resultado es -1, significa que la inserción falló
            success = result != -1;

        } catch (Exception e) {
            Log.e("Add Category Error", "Error al agregar categoría: " + e.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return success;
    }

    public boolean editCategory(int id, String name, int status) {
        SQLiteDatabase database = this.getWritableDatabase();
        boolean success = false;

        try {
            // Crear un ContentValues con los nuevos valores para la categoría
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("status", status);

            // Actualizar la categoría en la tabla usando el id como criterio
            int result = database.update(TABLE_CATEGORY, values, "id = ?", new String[]{String.valueOf(id)});

            // Si el resultado es mayor a 0, significa que la actualización fue exitosa
            success = result > 0;

        } catch (Exception e) {
            Log.e("Edit Category Error", "Error al editar categoría: " + e.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return success;
    }


    @SuppressLint("Range")
    public List<String> getCategoryName() {
        List<String> brandList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT name FROM " + TABLE_CATEGORY;
            cursor = database.rawQuery(query, null);

            // Recorrer el cursor y agregar marcas a la lista
            if (cursor.moveToFirst()) {
                do {
                    String nameProduct = cursor.getString(cursor.getColumnIndex("name"));
                    brandList.add(nameProduct);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Get Category Error", "Error al obtener categorias: " + e.getMessage());
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

    public Integer getIDCategory(String nameCategory){
        Integer categoryId = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT id FROM " + TABLE_CATEGORY + " WHERE name = ?";
            cursor = database.rawQuery(query, new String[]{nameCategory});

            if (cursor.moveToFirst()) {
                categoryId = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e("Get Category ID Error", "Error al obtener ID del category: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return categoryId;
    }
}
