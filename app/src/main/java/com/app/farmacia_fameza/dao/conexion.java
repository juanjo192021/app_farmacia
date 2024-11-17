package com.app.farmacia_fameza.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexion extends SQLiteOpenHelper {
    private static final String DB_NAME = "Farmacia.db";
    private static final int DB_VERSION = 7;

    // Table Names
    public static final String TABLE_BRAND = "Brand";
    public static final String TABLE_CATEGORY = "Category";
    public static final String TABLE_ROLE = "Role";
    public static final String TABLE_USER = "User";
    public static final String TABLE_PRODUCT = "Product";
    public static final String TABLE_PRODUCT_ENTRY = "Product_Entry";
    public static final String TABLE_PRODUCT_ENTRY_DETAIL = "Product_Entry_Detail";
    public static final String TABLE_PRODUCT_OUTPUT = "Product_Output";
    public static final String TABLE_PRODUCT_OUTPUT_DETAIL = "Product_Output_Detail";
    public static final String TABLE_SUPPLIER = "Supplier";
    public static final String TABLE_INVENTORY_TRANSACTION = "Inventory_Transaction";

    // Constructor
    public conexion(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Brand table
        db.execSQL("CREATE TABLE " + TABLE_BRAND + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "status INTEGER DEFAULT 1)");

        // Create Category table
        db.execSQL("CREATE TABLE " + TABLE_CATEGORY + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "status INTEGER DEFAULT 1)");

        // Create Role table
        db.execSQL("CREATE TABLE " + TABLE_ROLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "status INTEGER DEFAULT 1)");

        // Create User table
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

        // Create Supplier table
        db.execSQL("CREATE TABLE " + TABLE_SUPPLIER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "contact_phone TEXT, " +
                "email TEXT)");

        // Create Product table
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

        // Create Product_Entry table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_ENTRY + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "number_entry TEXT NOT NULL, " +
                "date_entry TEXT NOT NULL, " +
                "supplier_id INTEGER NOT NULL, " +
                "FOREIGN KEY (supplier_id) REFERENCES " + TABLE_SUPPLIER + "(id))");

        // Create Product_Entry_Detail table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_ENTRY_DETAIL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "entry_id INTEGER, " +
                "product_id INTEGER, " +
                "quantity INTEGER NOT NULL, " +
                "expiration_date TEXT NOT NULL, " +
                "production_date TEXT NOT NULL, " +
                "alert_date TEXT NOT NULL, " +
                "FOREIGN KEY (entry_id) REFERENCES " + TABLE_PRODUCT_ENTRY + "(id), " +
                "FOREIGN KEY (product_id) REFERENCES " + TABLE_PRODUCT + "(id))");

        // Create Product_Output table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_OUTPUT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "output_code TEXT NOT NULL, " +
                "output_date TEXT NOT NULL, " +
                "user_id INTEGER, " +
                "FOREIGN KEY (user_id) REFERENCES " + TABLE_USER + "(id))");

        // Create Product_Output_Detail table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_OUTPUT_DETAIL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "output_id INTEGER, " +
                "product_id INTEGER, " +
                "quantity INTEGER NOT NULL, " +
                "FOREIGN KEY (output_id) REFERENCES " + TABLE_PRODUCT_OUTPUT + "(id), " +
                "FOREIGN KEY (product_id) REFERENCES " + TABLE_PRODUCT + "(id))");

        // Create Inventory_Transaction table
        db.execSQL("CREATE TABLE " + TABLE_INVENTORY_TRANSACTION + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "transaction_type TEXT NOT NULL, " +
                "transaction_id INTEGER, " +
                "detail TEXT NOT NULL)");

        // Insertar datos en la tabla Brand
        db.execSQL("INSERT INTO " + TABLE_BRAND + " (name, status) VALUES " +
                "('Laboratorios Bayer', 1), " +
                "('Pfizer', 1), " +
                "('La Roche', 1), " +
                "('Merck', 1)");

        // Insertar datos en la tabla Category
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (name, status) VALUES " +
                "('Analgésicos', 1), " +
                "('Antibióticos', 1), " +
                "('Suplementos', 1), " +
                "('Antihistamínicos', 1)");

        // Insertar datos en la tabla Role
        db.execSQL("INSERT INTO " + TABLE_ROLE + " (name, description, status) VALUES " +
                "('Administrador', 'Acceso completo al sistema', 1), " +
                "('Vendedor', 'Acceso limitado para gestionar ventas', 1), " +
                "('Almacén', 'Acceso para gestionar inventario', 1)");

        // Insertar datos en la tabla User
        db.execSQL("INSERT INTO " + TABLE_USER + " (first_name, last_name, email, password, date_birth, cell_phone, role_id, status) VALUES " +
                "('Juan', 'Perez', 'juan.perez@example.com', 'password123', '1985-04-15', '555123456', 1, 1), " +
                "('Maria', 'Lopez', 'maria.lopez@example.com', 'password123', '1990-09-22', '555987654', 2, 1), " +
                "('Carlos', 'Ramirez', 'carlos.ramirez@example.com', 'password123', '1988-06-10', '555654321', 3, 1)");

        // Insertar datos en la tabla Supplier
        db.execSQL("INSERT INTO " + TABLE_SUPPLIER + " (name, contact_phone, email) VALUES " +
                "('Proveedor Farmacéutico A', '555111222', 'proveedora@example.com'), " +
                "('Distribuidora Medicinal B', '555333444', 'distribuidorab@example.com'), " +
                "('Lab Salud C', '555555666', 'labsaludc@example.com')");

        // Insertar datos en la tabla Product
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (sku, name, description, image, stock, unit_price, brand_id, category_id, status) VALUES " +
                "('SKU001', 'Paracetamol 500mg', 'Alivio del dolor y reducción de fiebre.', 'https://res.cloudinary.com/dwx7qadjn/image/upload/v1730146398/Farmacia/Paracetamol_w0lzpq.png', 100, 5.99, 1, 1, 1), " +
                "('SKU002', 'Amoxicilina 500mg', 'Antibiótico para infecciones bacterianas.', 'https://res.cloudinary.com/dwx7qadjn/image/upload/v1730144177/Farmacia/Amoxicilina_uwibjr.png', 50, 12.49, 2, 2, 1), " +
                "('SKU003', 'Vitamina C 1000mg', 'Suplemento de vitamina C para el sistema inmunológico.', 'https://res.cloudinary.com/dwx7qadjn/image/upload/v1730238645/Farmacia/VitaminaC_oalopf.png', 200, 8.99, 3, 3, 1), " +
                "('SKU004', 'Loratadina 10mg', 'Antihistamínico para aliviar alergias.', 'https://res.cloudinary.com/dwx7qadjn/image/upload/v1730238706/Farmacia/Loratadina_grxdca.jpg', 80, 4.99, 4, 4, 1)");

        // Insertar datos en la tabla Product_Entry
        db.execSQL("INSERT INTO " + TABLE_PRODUCT_ENTRY + " (number_entry, date_entry, supplier_id) VALUES " +
                "('ENT001', '2024-01-10', 1), " +
                "('ENT002', '2024-01-12', 2), " +
                "('ENT003', '2024-01-15', 3)");

        // Insertar datos en la tabla Product_Entry_Detail
        db.execSQL("INSERT INTO " + TABLE_PRODUCT_ENTRY_DETAIL + " (entry_id, product_id, quantity, expiration_date, production_date, alert_date) VALUES " +
                "(1, 1, 50, '2025-01-10', '2023-12-01', '2024-12-10'), " +
                "(1, 2, 30, '2025-06-10', '2024-01-01', '2025-05-10'), " +
                "(2, 3, 100, '2024-12-10', '2023-11-01', '2024-11-10'), " +
                "(3, 4, 70, '2025-03-15', '2024-02-10', '2025-02-15')");

        // Insertar datos en la tabla Product_Output
        db.execSQL("INSERT INTO " + TABLE_PRODUCT_OUTPUT + " (output_code, output_date, user_id) VALUES " +
                "('OUT001', '2024-01-20', 1), " +
                "('OUT002', '2024-01-25', 2), " +
                "('OUT003', '2024-02-01', 3)");

        // Insertar datos en la tabla Product_Output_Detail
        db.execSQL("INSERT INTO " + TABLE_PRODUCT_OUTPUT_DETAIL + " (output_id, product_id, quantity) VALUES " +
                "(1, 1, 10), " +
                "(1, 2, 5), " +
                "(2, 3, 20), " +
                "(3, 4, 15)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_ENTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_ENTRY_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OUTPUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OUTPUT_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIER);
        onCreate(db);
    }
}
