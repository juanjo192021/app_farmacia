package com.app.farmacia_fameza.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexion extends SQLiteOpenHelper {
    private static final String DB_NAME = "Farmacia.db";
    private static final int DB_VERSION = 2;

    // Nombres de las tablas
    public static final String TABLE_BRAND = "brand";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_ROLE = "role";
    public static final String TABLE_USER = "user";
    public static final String TABLE_SUPPLIER = "supplier"; // Tabla de proveedor
    public static final String TABLE_LOTE = "lote"; // Tabla de lote

    // Constructor
    public conexion(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creación de las tablas
        db.execSQL("CREATE TABLE " + TABLE_BRAND + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "status INTEGER DEFAULT 1)");

        db.execSQL("CREATE TABLE " + TABLE_CATEGORY + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "status INTEGER DEFAULT 1)");

        db.execSQL("CREATE TABLE " + TABLE_ROLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "status INTEGER DEFAULT 1)");

        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "first_name TEXT NOT NULL, " +
                "last_name TEXT NOT NULL, " +
                "email TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "date_birth TEXT, " +
                "cell_phone TEXT, " +
                "role_id INTEGER, " +
                "status INTEGER DEFAULT 1, " +
                "FOREIGN KEY (role_id) REFERENCES " + TABLE_ROLE + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sku TEXT UNIQUE, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "image TEXT, " +
                "stock INTEGER DEFAULT 0, " +
                "unit_price DECIMAL(10, 2) NOT NULL, " +
                "brand_id INTEGER, " +
                "category_id INTEGER, " +
                "status INTEGER DEFAULT 1, " +
                "FOREIGN KEY (brand_id) REFERENCES " + TABLE_BRAND + "(id), " +
                "FOREIGN KEY (category_id) REFERENCES " + TABLE_CATEGORY + "(id))");

        db.execSQL("CREATE TABLE " + TABLE_SUPPLIER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "contact_phone TEXT, " +
                "email TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_LOTE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lote_number TEXT NOT NULL, " +
                "expiration_date TEXT NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "supplier_id INTEGER NOT NULL, " +
                "FOREIGN KEY (product_id) REFERENCES " + TABLE_PRODUCT + "(id), " +
                "FOREIGN KEY (supplier_id) REFERENCES " + TABLE_SUPPLIER + "(id))");

        // Inserción de datos de ejemplo

        // Tabla Role
        db.execSQL("INSERT INTO " + TABLE_ROLE + " (name, description, status) VALUES ('admin', 'Administrador del Sistema', 1)");

        // Tabla User
        db.execSQL("INSERT INTO " + TABLE_USER + " (first_name, last_name, email, password, date_birth, cell_phone, role_id, status) " +
                "VALUES ('example', 'bb', 'example@gmail.com', '123', '2015-12-14', '987654123', 1, 1)");

        // Tabla Brand
        db.execSQL("INSERT INTO " + TABLE_BRAND + " (name, status) VALUES ('Brand A', 1)");
        db.execSQL("INSERT INTO " + TABLE_BRAND + " (name, status) VALUES ('Brand B', 1)");

        // Tabla Category
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (name, status) VALUES ('Category A', 1)");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (name, status) VALUES ('Category B', 1)");

        // Tabla Product
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (sku, name, description, image, stock, unit_price, brand_id, category_id, status) " +
                "VALUES ('1001', 'Producto 1', 'Descripción del Producto 1', 'image_url_1', 50, 10.50, 1, 1, 1)");
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (sku, name, description, image, stock, unit_price, brand_id, category_id, status) " +
                "VALUES ('1002', 'Producto 2', 'Descripción del Producto 2', 'image_url_2', 20, 15.75, 2, 2, 1)");

        // Tabla Supplier
        db.execSQL("INSERT INTO " + TABLE_SUPPLIER + " (name, contact_phone, email) VALUES ('Supplier A', '987654321', 'supplierA@gmail.com')");
        db.execSQL("INSERT INTO " + TABLE_SUPPLIER + " (name, contact_phone, email) VALUES ('Supplier B', '987654322', 'supplierB@gmail.com')");

        // Tabla Lote
        db.execSQL("INSERT INTO " + TABLE_LOTE + " (lote_number, expiration_date, quantity, product_id, supplier_id) " +
                "VALUES ('L001', '2024-12-31', 20, 1, 1)");
        db.execSQL("INSERT INTO " + TABLE_LOTE + " (lote_number, expiration_date, quantity, product_id, supplier_id) " +
                "VALUES ('L002', '2025-01-31', 15, 2, 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAND);
        onCreate(db);
    }
}
