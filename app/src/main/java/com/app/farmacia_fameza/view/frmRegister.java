package com.app.farmacia_fameza.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.farmacia_fameza.MainActivity;
import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.business.bUser;
import com.app.farmacia_fameza.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class frmRegister extends AppCompatActivity {
    EditText correo, contra, cell, fecha, nombre, apellido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        correo = findViewById(R.id.txtCorreo);
        contra = findViewById(R.id.txtPassword);
        cell = findViewById(R.id.txtcellphone);
        fecha = findViewById(R.id.txtbirthday);
        nombre = findViewById(R.id.txtfirstName);
        apellido = findViewById(R.id.txtlastName);

    }
    public void register(View view) {

        String emailStr = correo.getText().toString().trim();
        String passwordStr = contra.getText().toString().trim();
        String phoneStr = cell.getText().toString().trim();
        String birthDateStr = fecha.getText().toString().trim();
        String firstNameStr = nombre.getText().toString().trim();
        String lastNameStr = apellido.getText().toString().trim();


        String validationError = validateFields(emailStr, passwordStr, phoneStr, birthDateStr, firstNameStr, lastNameStr);
        if (validationError != null) {
            Toast.makeText(this, validationError, Toast.LENGTH_SHORT).show();
            return;
        }
        Date birthDateFormat = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            birthDateFormat = sdf.parse(birthDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid birth date format", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User();
        newUser.setEmail(emailStr);
        newUser.setPassword(passwordStr);
        newUser.setCell_phone(phoneStr);
        newUser.setDate_birth(birthDateFormat);
        newUser.setFirst_name(firstNameStr);
        newUser.setLast_name(lastNameStr);

        bUser userManager = new bUser(this);
        boolean success = userManager.register(newUser);

        if (success) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(frmRegister.this, MainActivity.class);
            startActivity(intent);
        } else {

            Toast.makeText(this, "Error registering user", Toast.LENGTH_SHORT).show();
        }
    }


    private String validateFields(String email, String password, String phone, String birthDate, String firstName, String lastName) {

        if (email.isEmpty() || password.isEmpty() || phone.isEmpty() || birthDate.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            return "Please fill in all fields.";
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Please enter a valid email address.";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters.";
        }
        return null;
    }
}