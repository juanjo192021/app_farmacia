package com.app.farmacia_fameza.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.farmacia_fameza.dao.conexion;
import com.app.farmacia_fameza.models.Role;
import com.app.farmacia_fameza.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;


public class cUser extends conexion{
    Context context;

    public cUser(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public boolean login(User user) {
        // Usar directamente la conexión existente
        SQLiteDatabase database = this.getReadableDatabase();
        Log.d("Login", "Email: " + user.getEmail() + ", Password: " + user.getPassword());

        try {
            // Ejecutar la consulta SQL
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE email=? AND password=?", new String[]{user.getEmail(), user.getPassword()});

            // Validar si el usuario fue encontrado
            boolean isAuthenticated = cursor.moveToFirst();

            if (isAuthenticated) {
                Log.d("Login", "Usuario autenticado exitosamente");
            } else {
                Log.d("Login", "Usuario no encontrado o credenciales incorrectas");
            }

            // Cerrar el cursor después de usarlo
            cursor.close();
            return isAuthenticated;
        } catch (Exception e) {
            Log.e("Login Error", "Error al intentar hacer login: " + e.getMessage());
            return false;
        } finally {
            // Asegurarse de cerrar la base de datos después de usarla
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public boolean registerUser(User user) {
        // Abrir la base de datos para escribir
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            // Crear un objeto ContentValues para almacenar los valores de los campos
            ContentValues values = new ContentValues();
            values.put("first_name", user.getFirst_name());
            values.put("last_name", user.getLast_name());
            values.put("email", user.getEmail());
            values.put("password", user.getPassword());
            // Convertir el objeto Date a String en formato "yyyy-MM-dd"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(user.getDate_birth());
            values.put("date_birth", dateString); // Almacenar la fecha como String
            values.put("cell_phone", user.getCell_phone());
            values.put("role", user.getRole().getId());
            values.put("status", user.getStatus());

            Log.d("Register", "Datos:"+values);

            // Insertar el nuevo usuario en la base de datos
            long result = database.insert(TABLE_USER, null, values);

            if (result == -1) {
                Log.e("Register", "Error al registrar el usuario");
                return false;
            } else {
                Log.d("Register", "Usuario registrado exitosamente con ID: " + result);
                return true;
            }
        } catch (Exception e) {
            Log.e("Register Error", "Error al registrar usuario: " + e.getMessage());
            return false;
        } finally {
            // Cerrar la base de datos después de usarla
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }
}
