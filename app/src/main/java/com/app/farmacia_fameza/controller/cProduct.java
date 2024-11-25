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
import com.app.farmacia_fameza.dto.ProductInventoryDTO;
import com.app.farmacia_fameza.dto.ProductKardexDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.dto.ProductUpdateDTO;
import com.app.farmacia_fameza.models.Product;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            String query = "SELECT id, name, stock " +
                    "FROM " + TABLE_PRODUCT + " p " +
                    "ORDER BY name ASC"; // Puedes ajustar el orden si es necesario

            cursor = database.rawQuery(query, null);

            // Recorrer el cursor y agregar productos a la lista
            if (cursor.moveToFirst()) {
                do {
                    ProductListDTO product = new ProductListDTO();
                    product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    product.setName(cursor.getString(cursor.getColumnIndex("name")));
                    product.setUnit_price(searchPriceProduct(cursor.getInt(cursor.getColumnIndex("id"))));
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
    public Product getDetailProduct(int productId) {
        Product product = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT p.*, c.name AS category_name, b.name AS brand_name " +
                    "FROM " + TABLE_PRODUCT + " p " +
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
                product.setUnit_price(searchPriceProduct(cursor.getInt(cursor.getColumnIndex("id"))));
                product.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                String categoryName = cursor.getString(cursor.getColumnIndex("category_name")); // Nombre de la categoría
                String brandName = cursor.getString(cursor.getColumnIndex("brand_name")); // Nombre de la marca
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

    public Integer searchIdHistory(Double price){
        int idHistory = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT id FROM " + TABLE_HISTORY_PRICE_PRODUCT + " WHERE price = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(price)});
            if (cursor.moveToFirst()) {
                idHistory = cursor.getInt(0);
            }
        }catch (Exception e){
            Log.e("Get Search ID Price Product Error", "Error al obtener el precio del producto: " + e.getMessage());
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return idHistory;
    }

    public boolean insertProduct(ProductAddDTO productAddDTO){
        SQLiteDatabase database = this.getWritableDatabase();
        int idBrand = cBrand.getIDBrand(productAddDTO.getBrand());
        int idCategory = cCategory.getIDCategory(productAddDTO.getCategory());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());  // Fecha y hora actuales
        ContentValues values = new ContentValues();
        ContentValues price = new ContentValues();
        try{
            values.put("sku", productAddDTO.getSku());
            values.put("name", productAddDTO.getName());
            values.put("description", productAddDTO.getDescription());
            values.put("image", productAddDTO.getImage());
            values.put("brand_id",idBrand);
            values.put("category_id",idCategory);
            values.put("stock", 0);
            values.put("status", 1);

            price.put("product_id", searchIdProduct(productAddDTO.getSku()));
            price.put("price", productAddDTO.getUnit_price());
            price.put("date_register", currentDateAndTime);

            Log.d("Insert Product", "Datos:" + values);
            long result = database.insert(TABLE_PRODUCT, null, values);
            long result2 = database.insert(TABLE_HISTORY_PRICE_PRODUCT, null, price);
            if (result == -1 && result2 == -1) {
                Log.e("Insert Product", "Error al insertar el producto");
                return false;
            } else {
                Log.d("Insert Product", "Producto insertado exitosamente con ID: ");
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());  // Fecha y hora actuales
        ContentValues values = new ContentValues();
        ContentValues price = new ContentValues();
        try{
            values.put("name", productUpdateDTO.getName());
            values.put("description", productUpdateDTO.getDescription());
            values.put("image", productUpdateDTO.getImage());

            price.put("product_id", productUpdateDTO.getId());
            price.put("price", productUpdateDTO.getUnit_price());
            price.put("date_register", currentDateAndTime);

            values.put("brand_id",idBrand);
            values.put("category_id",idCategory);
            values.put("status", productUpdateDTO.getStatus());
            String whereClause = "id=?";
            String[] whereArgs = { String.valueOf(productUpdateDTO.getId()) };

            int rowsAffected = database.update(TABLE_PRODUCT, values, whereClause, whereArgs);
            long result2 = database.insert(TABLE_HISTORY_PRICE_PRODUCT, null, price);

            if (rowsAffected > 0 && result2 == -1) {
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

    public String searchSKU(Integer id){
        String sku = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT sku FROM " + TABLE_PRODUCT + " WHERE id = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                sku = cursor.getString(0);
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
        return sku;
    }

    public boolean validateRegisterKardex(Integer idProduct){
        boolean band = false;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_PRODUCT_ENTRY_DETAIL + " WHERE product_id = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(idProduct)});
            if (cursor.moveToFirst()) {
                band = true;
            }
        }catch (Exception e){
            Log.e("Get Search Validate Product Error", "Error al validar el producto: " + e.getMessage());
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return band;
    }

    public String searchProductName(String sku){
        String productName = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT name FROM " + TABLE_PRODUCT + " WHERE sku = ?";
            cursor = database.rawQuery(query, new String[]{sku});
            if (cursor.moveToFirst()) {
                productName = cursor.getString(0);
            }
        }catch (Exception e){
            Log.e("Get Search ProductName Error", "Error al obtener nombre del producto: " + e.getMessage());
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return productName;
    }

    public Integer searchIdProduct(String sku){
        Integer id = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT id FROM " + TABLE_PRODUCT + " WHERE sku = ?";
            cursor = database.rawQuery(query, new String[]{sku});
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
        }catch (Exception e){
            Log.e("Get Search ID Error", "Error al obtener ID del producto: " + e.getMessage());
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return id;
    }

    public Double searchPriceProductByID(Integer id){
        double price = 0.0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_HISTORY_PRICE_PRODUCT +
                    " WHERE product_id = ? " +
                    " ORDER BY date_register DESC " +
                    " LIMIT 1";
            cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                price = cursor.getDouble(2);
            }
        }catch (Exception e){
            Log.e("Get Search price Error", "Error al obtener precio del producto: " + e.getMessage());
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

    public Integer getIDProductBySKU(String sku) {
        Integer productId = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT id FROM " + TABLE_PRODUCT + " WHERE sku = ?";
            cursor = database.rawQuery(query, new String[]{sku});

            if (cursor.moveToFirst()) {
                productId = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e("Get Product ID Error", "Error al obtener ID del producto: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return productId;
    }

    public void updateProductStock(int productId, int quantity, boolean isAddition) {
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            // Obtener la cantidad actual de stock
            int currentStock = getCurrentStock(productId, database);

            // Calcular el nuevo stock basado en si es una suma o una resta
            int newStock = isAddition ? currentStock + quantity : currentStock - quantity;

            // Asegurarse de que el stock no sea negativo
            if (newStock < 0) {
                Log.e("Update Product Stock", "No se puede reducir el stock por debajo de cero para el producto ID: " + productId);
                return;
            }

            // Crear valores para la actualización
            ContentValues values = new ContentValues();
            values.put("stock", newStock);

            // Actualizar el stock en la tabla de productos
            database.update(TABLE_PRODUCT, values, "id = ?", new String[]{String.valueOf(productId)});

            Log.d("Update Product Stock", "Stock actualizado para producto ID: " + productId + " a " + newStock);

        } catch (Exception e) {
            Log.e("Update Stock Error", "Error al actualizar el stock para producto ID: " + productId + " - " + e.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    private int getCurrentStock(int productId, SQLiteDatabase database) {
        int stock = 0;
        Cursor cursor = null;

        try {
            String query = "SELECT stock FROM " + TABLE_PRODUCT + " WHERE id = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(productId)});

            if (cursor.moveToFirst()) {
                stock = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e("Get Current Stock Error", "Error al obtener el stock actual para producto ID: " + productId + " - " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return stock;
    }

    /*public List<ProductKardexDTO> getProductData(Integer id){
        List<ProductKardexDTO> productEntries = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT ped.entry_id, pe.date_entry, ped.alert_date, ped.expiration_date, s.name, " +
                "COALESCE(ped.quantity, 0) AS entry_quantity, COALESCE(pod.quantity, 0) AS output_quantity, " +
                "COALESCE(ped.quantity, 0) - COALESCE(pod.quantity, 0) AS remaining_quantity " +
                "FROM " + TABLE_PRODUCT_ENTRY_DETAIL + " ped " +
                "INNER JOIN "+ TABLE_PRODUCT_ENTRY + " pe ON ped.entry_id = pe.id " +
                "INNER JOIN " + TABLE_SUPPLIER  + " s ON pe.supplier_id = s.id " +
                "LEFT JOIN " + TABLE_PRODUCT_OUTPUT_DETAIL + " pod ON ped.product_id = pod.product_id " +
                "WHERE ped.product_id = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int entryId = cursor.getInt(0);
                String dateEntry = cursor.getString(1);
                String alertDate = cursor.getString(2);
                String expirationDate = cursor.getString(3);
                String supplierName = cursor.getString(4);
                int entryQuantity = cursor.getInt(5);
                int outputQuantity = cursor.getInt(6);
                int remainingQuantity = cursor.getInt(7);

                productEntries.add(new ProductKardexDTO(entryId, dateEntry, alertDate, expirationDate,
                        supplierName, entryQuantity, outputQuantity, remainingQuantity));
            } while (cursor.moveToNext());
            cursor.close();
        }
        database.close();
        return productEntries;
    }*/

    public List<ProductInventoryDTO> completeTableKardexFilterMonthSKU(String month, Integer idProduct){
        List<ProductInventoryDTO> productInventoryDTOList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT " +
                "p.sku AS sku, " +
                "p.name AS nameProduct, " +
                "CASE " +
                "    WHEN it.transaction_type = 'entry' THEN ped.price_history " +
                "    WHEN it.transaction_type = 'output' THEN pod.price_history " +
                "END AS unit_price, " +
                "CASE " +
                "    WHEN it.transaction_type = 'entry' THEN pe.date_entry " +
                "    WHEN it.transaction_type = 'output' THEN po.output_date " +
                "END AS fecha, " +
                "it.detail AS detalle, " +
                "CASE " +
                "    WHEN it.transaction_type = 'entry' THEN ped.quantity " +
                "    ELSE 0 " +
                "END AS entrada, " +
                "CASE " +
                "    WHEN it.transaction_type = 'output' THEN pod.quantity " +
                "    ELSE 0 " +
                "END AS salida, " +
                "(SELECT SUM(CASE " +
                "            WHEN it2.transaction_type = 'entry' THEN ped2.quantity " +
                "            WHEN it2.transaction_type = 'output' THEN -pod2.quantity " +
                "            ELSE 0 " +
                "        END) " +
                " FROM " + TABLE_INVENTORY_TRANSACTION + " it2 " +
                " LEFT JOIN " + TABLE_PRODUCT_ENTRY + " pe2 ON it2.transaction_id = pe2.id AND it2.transaction_type = 'entry' " +
                " LEFT JOIN " + TABLE_PRODUCT_ENTRY_DETAIL + " ped2 ON pe2.id = ped2.entry_id " +
                " LEFT JOIN " + TABLE_PRODUCT_OUTPUT + " po2 ON it2.transaction_id = po2.id AND it2.transaction_type = 'output' " +
                " LEFT JOIN " + TABLE_PRODUCT_OUTPUT_DETAIL + " pod2 ON po2.id = pod2.output_id " +
                " WHERE (ped2.product_id = p.id OR pod2.product_id = p.id) " +
                "   AND (CASE " +
                "           WHEN it2.transaction_type = 'entry' THEN pe2.date_entry " +
                "           WHEN it2.transaction_type = 'output' THEN po2.output_date " +
                "       END) <= " +
                "       (CASE " +
                "           WHEN it.transaction_type = 'entry' THEN pe.date_entry " +
                "           WHEN it.transaction_type = 'output' THEN po.output_date " +
                "       END)" +
                ") AS saldo " +
                "FROM " + TABLE_INVENTORY_TRANSACTION + " it " +
                "LEFT JOIN " + TABLE_PRODUCT_ENTRY + " pe ON it.transaction_id = pe.id AND it.transaction_type = 'entry' " +
                "LEFT JOIN " + TABLE_PRODUCT_ENTRY_DETAIL + " ped ON pe.id = ped.entry_id " +
                "LEFT JOIN " + TABLE_PRODUCT_OUTPUT + " po ON it.transaction_id = po.id AND it.transaction_type = 'output' " +
                "LEFT JOIN " + TABLE_PRODUCT_OUTPUT_DETAIL + " pod ON po.id = pod.output_id " +
                "LEFT JOIN " + TABLE_PRODUCT + " p ON ped.product_id = p.id OR pod.product_id = p.id " +
                "WHERE p.id = ? AND " +
                "strftime('%m', " +
                "       CASE " +
                "           WHEN it.transaction_type = 'entry' THEN pe.date_entry " +
                "           WHEN it.transaction_type = 'output' THEN po.output_date " +
                "       END) = " +
                "       CASE ? " +
                "           WHEN 'enero' THEN '01' " +
                "           WHEN 'febrero' THEN '02' " +
                "           WHEN 'marzo' THEN '03' " +
                "           WHEN 'abril' THEN '04' " +
                "           WHEN 'mayo' THEN '05' " +
                "           WHEN 'junio' THEN '06' " +
                "           WHEN 'julio' THEN '07' " +
                "           WHEN 'agosto' THEN '08' " +
                "           WHEN 'septiembre' THEN '09' " +
                "           WHEN 'octubre' THEN '10' " +
                "           WHEN 'noviembre' THEN '11' " +
                "           WHEN 'diciembre' THEN '12' " +
                "       END " +
                "ORDER BY fecha;";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(idProduct),month});
        if (cursor != null && cursor.moveToFirst()) {
            do{
                String sku = cursor.getString(0);
                String nameProduct = cursor.getString(1);
                Double precio = cursor.getDouble(2);
                String fecha = cursor.getString(3);
                String detalle = cursor.getString(4);
                Integer entrada = cursor.getInt(5);
                Integer salida = cursor.getInt(6);
                Integer saldo = cursor.getInt(7);

                productInventoryDTOList.add(new ProductInventoryDTO(sku,nameProduct,fecha,detalle,entrada,salida,saldo,precio));
            }while(cursor.moveToNext());
            cursor.close();
        }
        database.close();
        return productInventoryDTOList;
    }
    /*public List<ProductInventoryDTO>  completeTableKardex(){
        List<ProductInventoryDTO> productInventoryDTOList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT " +
                "p.sku AS sku, " +
                "p.name AS nameProduct, " +
                "CASE " +
                "    WHEN it.transaction_type = 'entry' THEN pe.date_entry " +
                "    WHEN it.transaction_type = 'output' THEN po.output_date " +
                "END AS fecha, " +
                "it.detail AS detalle, " +
                "CASE " +
                "    WHEN it.transaction_type = 'entry' THEN ped.quantity " +
                "    ELSE 0 " +
                "END AS entrada, " +
                "CASE " +
                "    WHEN it.transaction_type = 'output' THEN pod.quantity " +
                "    ELSE 0 " +
                "END AS salida, " +
                "(SELECT SUM(CASE " +
                "            WHEN it2.transaction_type = 'entry' THEN ped2.quantity " +
                "            WHEN it2.transaction_type = 'output' THEN -pod2.quantity " +
                "            ELSE 0 " +
                "        END) " +
                " FROM " + TABLE_INVENTORY_TRANSACTION + " it2 " +
                " LEFT JOIN " + TABLE_PRODUCT_ENTRY + " pe2 ON it2.transaction_id = pe2.id AND it2.transaction_type = 'entry' " +
                " LEFT JOIN " + TABLE_PRODUCT_ENTRY_DETAIL + " ped2 ON pe2.id = ped2.entry_id " +
                " LEFT JOIN " + TABLE_PRODUCT_OUTPUT + " po2 ON it2.transaction_id = po2.id AND it2.transaction_type = 'output' " +
                " LEFT JOIN " + TABLE_PRODUCT_OUTPUT_DETAIL + " pod2 ON po2.id = pod2.output_id " +
                " WHERE (ped2.product_id = p.id OR pod2.product_id = p.id) " +
                "   AND (CASE " +
                "           WHEN it2.transaction_type = 'entry' THEN pe2.date_entry " +
                "           WHEN it2.transaction_type = 'output' THEN po2.output_date " +
                "       END) <= " +
                "       (CASE " +
                "           WHEN it.transaction_type = 'entry' THEN pe.date_entry " +
                "           WHEN it.transaction_type = 'output' THEN po.output_date " +
                "       END)" +
                ") AS saldo " +
                "FROM " + TABLE_INVENTORY_TRANSACTION + " it " +
                "LEFT JOIN " + TABLE_PRODUCT_ENTRY + " pe ON it.transaction_id = pe.id AND it.transaction_type = 'entry' " +
                "LEFT JOIN " + TABLE_PRODUCT_ENTRY_DETAIL + " ped ON pe.id = ped.entry_id " +
                "LEFT JOIN " + TABLE_PRODUCT_OUTPUT + " po ON it.transaction_id = po.id AND it.transaction_type = 'output' " +
                "LEFT JOIN " + TABLE_PRODUCT_OUTPUT_DETAIL + " pod ON po.id = pod.output_id " +
                "LEFT JOIN " + TABLE_PRODUCT + " p ON ped.product_id = p.id OR pod.product_id = p.id " +
                "ORDER BY p.sku, fecha;";

        Cursor cursor = database.rawQuery(query,null);
        if (cursor != null && cursor.moveToFirst()) {
            do{
                String sku = cursor.getString(0);
                String nameProduct = cursor.getString(1);
                String fecha = cursor.getString(2);
                String detalle = cursor.getString(3);
                Integer entrada = cursor.getInt(4);
                Integer salida = cursor.getInt(5);
                Integer saldo = cursor.getInt(6);

                productInventoryDTOList.add(new ProductInventoryDTO(sku,nameProduct,fecha,entrada,detalle,salida,saldo));
            }while(cursor.moveToNext());
            cursor.close();
        }
        database.close();
        return  productInventoryDTOList;
    }*/
}
