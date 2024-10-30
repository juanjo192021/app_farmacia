package com.app.farmacia_fameza.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.farmacia_fameza.dao.conexion;
import com.app.farmacia_fameza.dto.ProductAddDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.dto.ProductUpdateDTO;
import com.app.farmacia_fameza.models.Brand;
import com.app.farmacia_fameza.models.Lote;
import com.app.farmacia_fameza.models.Product;

import java.util.ArrayList;
import java.util.List;

public class cProduct extends conexion {

    Context context;
    cBrand cBrand;
    cCategory cCategory;

    public cProduct(@Nullable Context context) {
        super(context);
        this.context = context;
        cBrand = new cBrand(context);
        cCategory = new cCategory(context);
    }

    @SuppressLint("Range")
    public List<ProductListDTO> getProducts() {
        List<ProductListDTO> productList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT id, name, unit_price, stock " +
                    "FROM " + TABLE_PRODUCT + " p " +
                    "ORDER BY name ASC"; // Puedes ajustar el orden si es necesario

            cursor = database.rawQuery(query, null);

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

    @SuppressLint("Range")
    public Product getDetailProduct(int productId) {
        Product product = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT p.*, l.lote_number, l.expiration_date, c.name AS category_name, b.name AS brand_name " +
                    "FROM " + TABLE_PRODUCT + " p " +
                    "LEFT JOIN " + TABLE_LOTE + " l ON p.lote_id = l.id " +
                    "LEFT JOIN " + TABLE_CATEGORY + " c ON p.category_id = c.id " +
                    "LEFT JOIN " + TABLE_BRAND + " b ON p.brand_id = b.id " +
                    "WHERE p.id = ?"; // Filtrar por ID

            cursor = database.rawQuery(query, new String[]{String.valueOf(productId)});

            // Si encontramos el producto
            if (cursor.moveToFirst()) {
                product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                product.setSku(cursor.getString(cursor.getColumnIndex("sku")));
                product.setName(cursor.getString(cursor.getColumnIndex("name")));
                product.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                product.setImage(cursor.getString(cursor.getColumnIndex("image")));
                product.setStock_actual(cursor.getInt(cursor.getColumnIndex("stock")));
                product.setUnit_price(cursor.getDouble(cursor.getColumnIndex("unit_price")));
                product.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                // Agregamos los campos adicionales
                String loteNumber = cursor.getString(cursor.getColumnIndex("lote_number")); // Número de lote
                String expirationDate = cursor.getString(cursor.getColumnIndex("expiration_date")); // Fecha de expiración
                String categoryName = cursor.getString(cursor.getColumnIndex("category_name")); // Nombre de la categoría
                String brandName = cursor.getString(cursor.getColumnIndex("brand_name")); // Nombre de la marca

                product.getLote().setLote_number(loteNumber);
                product.getLote().setExpiration_date(expirationDate);
                product.getCategory().setName(categoryName);
                product.getBrand().setName(brandName);
            }
        } catch (Exception e) {
            Log.e("Get Product Detail Error", "Error al obtener el producto: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return product;
    }

    public boolean insertProduct(ProductAddDTO productAddDTO){
        SQLiteDatabase database = this.getWritableDatabase();
        int idBrand = cBrand.getIDBrand(productAddDTO.getBrand());
        int idCategory = cCategory.getIDCategory(productAddDTO.getCategory());
        ContentValues values = new ContentValues();
        try{
            values.put("sku", productAddDTO.getSku());
            values.put("name", productAddDTO.getName());
            values.put("description", productAddDTO.getDescription());
            values.put("image", productAddDTO.getImage());
            values.put("unit_price", productAddDTO.getUnit_price());
            values.put("brand_id",idBrand);
            values.put("category_id",idCategory);
            values.put("stock", 0);
            values.putNull("lote_id");
            values.put("status", 1);

            Log.d("Insert Product", "Datos:" + values);
            long result = database.insert(TABLE_PRODUCT, null, values);
            if (result == -1) {
                Log.e("Insert Product", "Error al insertar el producto");
                return false;
            } else {
                Log.d("Insert Product", "Producto insertado exitosamente con ID: " + result);
                return true;
            }
        } catch (Exception e){
            Log.e("Insert Product Error", "Error al insertar producto: " + e.getMessage());
            return false;
        } finally {
            // Cerrar la base de datos después de usarla
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public boolean updateProduct(ProductUpdateDTO productUpdateDTO){
        SQLiteDatabase database = this.getWritableDatabase();
        int idBrand = cBrand.getIDBrand(productUpdateDTO.getBrand());
        int idCategory = cCategory.getIDCategory(productUpdateDTO.getCategory());
        try{
            ContentValues values = new ContentValues();
            values.put("name", productUpdateDTO.getName());
            values.put("description", productUpdateDTO.getDescription());
            values.put("image", productUpdateDTO.getImage());
            values.put("unit_price", productUpdateDTO.getUnit_price());
            values.put("brand_id",idBrand);
            values.put("category_id",idCategory);
            values.put("status", productUpdateDTO.getStatus());
            String whereClause = "id=?";
            String[] whereArgs = { String.valueOf(productUpdateDTO.getId()) };

            int rowsAffected = database.update(TABLE_PRODUCT, values, whereClause, whereArgs);

            if (rowsAffected > 0) {
                Log.d("Update Product", "Producto actualizado exitosamente con ID: " + productUpdateDTO.getId());
                return true;
            } else {
                Log.e("Update Product", "No se pudo actualizar el producto con ID: " + productUpdateDTO.getId());
                return false;
            }
        } catch (Exception e){
            Log.e("Update Product Error", "Error al actualizar el producto: " + e.getMessage());
            return false;
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public boolean existsProductById(Integer id) {
        boolean exists = false;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT 1 FROM " + TABLE_PRODUCT + " WHERE id = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                exists = true;
            }
        } catch (Exception e) {
            Log.e("Check Product Exists Error", "Error al verificar si el producto existe: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return exists;
    }

}
