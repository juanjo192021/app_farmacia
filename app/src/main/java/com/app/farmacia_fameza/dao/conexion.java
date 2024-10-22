package com.app.farmacia_fameza.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexion extends SQLiteOpenHelper {
    private static final String DB_NAME = "Farmacia.db";
    private static final int DB_VERSION = 4;

    // Table Names
    public static final String TABLE_BRAND = "Brand";
    public static final String TABLE_CATEGORY = "Category";
    public static final String TABLE_ROLE = "Role";
    public static final String TABLE_USER = "User";
    public static final String TABLE_PRODUCT = "Product";
    public static final String TABLE_LOTE = "Lote";
    public static final String TABLE_PRODUCT_ENTRY = "Product_Entry";
    public static final String TABLE_PRODUCT_ENTRY_DETAIL = "Product_Entry_Detail";
    public static final String TABLE_PRODUCT_OUTPUT = "Product_Output";
    public static final String TABLE_PRODUCT_OUTPUT_DETAIL = "Product_Output_Detail";
    public static final String TABLE_SUPPLIER = "Supplier";

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

        // Create Lote table
        db.execSQL("CREATE TABLE " + TABLE_LOTE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lote_number TEXT NOT NULL, " +
                "expiration_date TEXT NOT NULL, " +
                "production_date TEXT NOT NULL, " +
                "alert_date TEXT NOT NULL, " +
                "quantity INTEGER NOT NULL)");

        // Create Product table with reference to Lote
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
                "lote_id INTEGER, " +
                "status INTEGER DEFAULT 1, " +
                "FOREIGN KEY (brand_id) REFERENCES " + TABLE_BRAND + "(id), " +
                "FOREIGN KEY (category_id) REFERENCES " + TABLE_CATEGORY + "(id), " +
                "FOREIGN KEY (lote_id) REFERENCES " + TABLE_LOTE + "(id))");

        // Create Product_Entry table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_ENTRY + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "entry_code TEXT NOT NULL, " +
                "entry_date TEXT NOT NULL, " +
                "user_id INTEGER NOT NULL, " +
                "supplier_id INTEGER, " +
                "FOREIGN KEY (user_id) REFERENCES " + TABLE_USER + "(id), " +
                "FOREIGN KEY (supplier_id) REFERENCES " + TABLE_SUPPLIER + "(id))");

        // Create Product_Entry_Detail table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_ENTRY_DETAIL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "entry_id INTEGER NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "FOREIGN KEY (entry_id) REFERENCES " + TABLE_PRODUCT_ENTRY + "(id), " +
                "FOREIGN KEY (product_id) REFERENCES " + TABLE_PRODUCT + "(id))");

        // Create Product_Output table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_OUTPUT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "output_code TEXT NOT NULL, " +
                "output_date TEXT NOT NULL, " +
                "user_id INTEGER NOT NULL, " +
                "FOREIGN KEY (user_id) REFERENCES " + TABLE_USER + "(id))");

        // Create Product_Output_Detail table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_OUTPUT_DETAIL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "output_id INTEGER NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "FOREIGN KEY (output_id) REFERENCES " + TABLE_PRODUCT_OUTPUT + "(id), " +
                "FOREIGN KEY (product_id) REFERENCES " + TABLE_PRODUCT + "(id))");

        // Inserción de datos de ejemplo

        // Insert data into Role table
        db.execSQL("INSERT INTO " + TABLE_ROLE + " (name, description, status) VALUES " +
                "('admin', 'Administrador del Sistema', 1), " +
                "('pharmacist', 'Farmacéutico', 1), " +
                "('manager', 'Gerente de la Farmacia', 1)");

        // Insert data into Brand table
        db.execSQL("INSERT INTO " + TABLE_BRAND + " (name, status) VALUES " +
                "('Pfizer', 1), " +
                "('Bayer', 1), " +
                "('GSK', 1), " +
                "('Johnson & Johnson', 1)");

        // Insert data into Category table
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (name, status) VALUES " +
                "('Analgesics', 1), " +
                "('Antibiotics', 1), " +
                "('Vitamins', 1), " +
                "('Antihistamines', 1)");

        // Insert data into Supplier table
        db.execSQL("INSERT INTO " + TABLE_SUPPLIER + " (name, contact_phone, email) VALUES " +
                "('Distribuidora Farmacéutica SA', '800-123-456', 'contacto@distribuidora.com'), " +
                "('Proveedores de Salud Ltda.', '800-987-654', 'info@proveedoresdesalud.com'), " +
                "('Farmacias Unidas', '800-321-654', 'ventas@farmaciasunidas.com')");

        // Insert data into Lote table
        db.execSQL("INSERT INTO " + TABLE_LOTE + " (lote_number, expiration_date, production_date, alert_date, quantity) VALUES " +
                "('L001', '2025-12-31', '2024-01-01', '2025-11-30', 200), " +
                "('L002', '2026-06-30', '2024-02-15', '2026-05-30', 150), " +
                "('L003', '2025-10-15', '2024-04-10', '2025-09-15', 100)");

        // Insert data into Product table
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (sku, name, description, image, stock, unit_price, brand_id, category_id, lote_id, status) VALUES " +
                "('SKU001', 'Paracetamol 500mg', 'Alivio del dolor y reducción de fiebre.', 'paracetamol.jpg', 100, 5.99, 1, 1, 1, 1), " +
                "('SKU002', 'Amoxicilina 500mg', 'Antibiótico para infecciones bacterianas.', 'amoxicilina.jpg', 50, 12.49, 2, 2, 2, 1), " +
                "('SKU003', 'Vitamina C 1000mg', 'Suplemento de vitamina C para el sistema inmunológico.', 'vitamina_c.jpg', 200, 8.99, 3, 3, 3, 1), " +
                "('SKU004', 'Loratadina 10mg', 'Antihistamínico para aliviar alergias.', 'loratadina.jpg', 80, 4.99, 4, 4, 1, 1)");

        // Insert data into User table
        db.execSQL("INSERT INTO " + TABLE_USER + " (first_name, last_name, email, password, date_birth, cell_phone, role_id, status) VALUES " +
                "('Ana', 'Gonzalez', 'ana.gonzalez@example.com', 'password123', '1990-03-15', '800-456-789', 1, 1), " +
                "('Luis', 'Martinez', 'luis.martinez@example.com', 'password123', '1985-07-22', '800-654-321', 2, 1), " +
                "('Carla', 'Lopez', 'carla.lopez@example.com', 'password123', '1992-11-01', '800-321-987', 3, 1)");

        // Insert data into Product_Entry table
        db.execSQL("INSERT INTO " + TABLE_PRODUCT_ENTRY + " (entry_code, entry_date, user_id, supplier_id) VALUES " +
                "('ENTRY001', '2024-10-20', 1, 1), " +
                "('ENTRY002', '2024-10-21', 2, 2), " +
                "('ENTRY003', '2024-10-22', 3, 3)");

        // Insert data into Product_Entry_Detail table
        db.execSQL("INSERT INTO " + TABLE_PRODUCT_ENTRY_DETAIL + " (entry_id, product_id, quantity) VALUES " +
                "(1, 1, 50), " +
                "(1, 2, 30), " +
                "(2, 3, 20), " +
                "(3, 4, 10)");

        // Insert data into Product_Output table
        db.execSQL("INSERT INTO " + TABLE_PRODUCT_OUTPUT + " (output_code, output_date, user_id) VALUES " +
                "('OUTPUT001', '2024-10-23', 1), " +
                "('OUTPUT002', '2024-10-24', 2)");

        // Insert data into Product_Output_Detail table
        db.execSQL("INSERT INTO " + TABLE_PRODUCT_OUTPUT_DETAIL + " (output_id, product_id, quantity) VALUES " +
                "(1, 1, 5), " +
                "(1, 2, 2), " +
                "(2, 3, 3)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_ENTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_ENTRY_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OUTPUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OUTPUT_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIER);
        onCreate(db);
    }
}
