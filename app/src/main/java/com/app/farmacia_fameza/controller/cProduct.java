package com.app.farmacia_fameza.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.farmacia_fameza.dao.conexion;
import com.app.farmacia_fameza.models.Product;

import java.util.ArrayList;
import java.util.List;

public class cProduct extends conexion {

    Context context;

    public cProduct(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("Range")
    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Consultar productos con su lote más reciente, obteniendo también la fecha de expiración
            String query = "SELECT p.*, l.expiration_date " +
                    "FROM " + TABLE_PRODUCT + " p " +
                    "LEFT JOIN " + TABLE_LOTE + " l ON p.id = l.product_id " +
                    "ORDER BY l.expiration_date DESC"; // Obtenemos el lote más reciente

            cursor = database.rawQuery(query, null); // No se utilizan LIMIT ni OFFSET

            // Recorrer el cursor y agregar productos a la lista
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    product.setSku(cursor.getString(cursor.getColumnIndex("sku")));
                    product.setName(cursor.getString(cursor.getColumnIndex("name")));
                    product.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    product.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    product.setStock_actual(cursor.getInt(cursor.getColumnIndex("stock")));
                    product.setUnit_price(cursor.getDouble(cursor.getColumnIndex("unit_price")));
                    product.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

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


    public boolean insertProduct(Product product) {
        // Abrir la base de datos para escritura
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            // Crear un objeto ContentValues para almacenar los valores de los campos
            ContentValues values = new ContentValues();
            values.put("sku", product.getSku());
            values.put("name", product.getName());
            values.put("description", product.getDescription());
            values.put("image", product.getImage());
            values.put("stock", product.getStock_actual());
            values.put("unit_price", product.getUnit_price());

            // Verificar si la marca no es nula antes de insertarla
            if (product.getBrand() != null) {
                values.put("brand", product.getBrand().getId());
            } else {
                values.putNull("brand"); // Si no hay marca, se inserta null
            }

            // Verificar si la categoría no es nula antes de insertarla
            if (product.getCategory() != null) {
                values.put("category", product.getCategory().getId());
            } else {
                values.putNull("category"); // Si no hay categoría, se inserta null
            }

            values.put("status", product.getStatus());

            Log.d("Insert Product", "Datos:" + values);

            // Insertar el nuevo producto en la base de datos
            long result = database.insert(TABLE_PRODUCT, null, values);

            if (result == -1) {
                Log.e("Insert Product", "Error al insertar el producto");
                return false;
            } else {
                Log.d("Insert Product", "Producto insertado exitosamente con ID: " + result);
                return true;
            }
        } catch (Exception e) {
            Log.e("Insert Product Error", "Error al insertar producto: " + e.getMessage());
            return false;
        } finally {
            // Cerrar la base de datos después de usarla
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public boolean changeStock(int productId, int quantity, boolean isIncrease) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            // Definir la cláusula WHERE para especificar qué producto actualizar
            String whereClause = "id=?";
            String[] whereArgs = { String.valueOf(productId) };

            // Primero, obtener el stock actual
            String[] columns = { "stock" };
            Cursor cursor = database.query(TABLE_PRODUCT, columns, whereClause, whereArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int currentStock = cursor.getInt(cursor.getColumnIndexOrThrow("stock"));
                cursor.close();

                int newStock;
                if (isIncrease) {
                    newStock = currentStock + quantity; // Incrementar stock
                } else {
                    if (currentStock < quantity) {
                        Log.e("Change Stock", "Stock insuficiente para decrementar. Stock actual: " + currentStock);
                        return false; // No se puede decrementar si el stock es insuficiente
                    }
                    newStock = currentStock - quantity; // Decrementar stock
                }

                // Actualizar el stock en la base de datos
                ContentValues values = new ContentValues();
                values.put("stock", newStock);

                int rowsAffected = database.update(TABLE_PRODUCT, values, whereClause, whereArgs);

                if (rowsAffected > 0) {
                    Log.d("Change Stock", "Stock actualizado exitosamente. Nuevo stock: " + newStock);
                    return true;
                } else {
                    Log.e("Change Stock", "No se pudo actualizar el stock para el producto con ID: " + productId);
                    return false;
                }
            } else {
                Log.e("Change Stock", "Producto no encontrado con ID: " + productId);
                return false;
            }
        } catch (Exception e) {
            Log.e("Change Stock Error", "Error al cambiar el stock: " + e.getMessage());
            return false;
        } finally {
            // Cerrar la base de datos después de usarla
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public boolean updateProduct(Product product) {
        // Abrir la base de datos para escritura
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            // Crear un objeto ContentValues para almacenar los valores de los campos que se van a actualizar
            ContentValues values = new ContentValues();
            values.put("sku", product.getSku());
            values.put("name", product.getName());
            values.put("description", product.getDescription());
            values.put("image", product.getImage());
            values.put("unit_price", product.getUnit_price());

            // Si la marca no es nula, agregarla a los valores a actualizar
            if (product.getBrand() != null) {
                values.put("brand_id", product.getBrand().getId());
            } else {
                values.putNull("brand_id"); // o puedes dejarlo fuera si no quieres modificar este campo
            }

            // Si la categoría no es nula, agregarla a los valores a actualizar
            if (product.getCategory() != null) {
                values.put("category_id", product.getCategory().getId());
            } else {
                values.putNull("category_id"); // o puedes dejarlo fuera si no quieres modificar este campo
            }

            values.put("status", product.getStatus());

            // Definir la cláusula WHERE para especificar qué producto actualizar
            String whereClause = "id=?";
            String[] whereArgs = { String.valueOf(product.getId()) };

            // Actualizar el producto en la base de datos
            int rowsAffected = database.update(TABLE_PRODUCT, values, whereClause, whereArgs);

            if (rowsAffected > 0) {
                Log.d("Update Product", "Producto actualizado exitosamente con ID: " + product.getId());
                return true;
            } else {
                Log.e("Update Product", "No se pudo actualizar el producto con ID: " + product.getId());
                return false;
            }
        } catch (Exception e) {
            Log.e("Update Product Error", "Error al actualizar el producto: " + e.getMessage());
            return false;
        } finally {
            // Cerrar la base de datos después de usarla
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public boolean deleteProduct(int productId) {
        // Abrir la base de datos para escritura
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            // Definir la cláusula WHERE para especificar qué producto eliminar
            String whereClause = "id=?";
            String[] whereArgs = { String.valueOf(productId) };

            // Eliminar el producto de la base de datos
            int rowsAffected = database.delete(TABLE_PRODUCT, whereClause, whereArgs);

            if (rowsAffected > 0) {
                Log.d("Delete Product", "Producto eliminado exitosamente con ID: " + productId);
                return true;
            } else {
                Log.e("Delete Product", "No se pudo eliminar el producto con ID: " + productId);
                return false;
            }
        } catch (Exception e) {
            Log.e("Delete Product Error", "Error al eliminar el producto: " + e.getMessage());
            return false;
        } finally {
            // Cerrar la base de datos después de usarla
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }


}
