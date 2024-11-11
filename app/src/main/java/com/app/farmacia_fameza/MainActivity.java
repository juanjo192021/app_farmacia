package com.app.farmacia_fameza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.farmacia_fameza.business.bUser;
import com.app.farmacia_fameza.models.User;
import com.app.farmacia_fameza.view.frmRegister;


public class MainActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    TextView Registrarse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtEmail = findViewById(R.id.txtNombreUsuario);
        txtPassword = findViewById(R.id.txtContrasenia);
        Button buttonLogin = findViewById(R.id.btnIniciarSesion);
        TextView txtRegistrarse = findViewById(R.id.txtRegistrarse);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bUser BUser = new bUser(MainActivity.this);

                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if(BUser.login(new User(email,password))){
                    Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                    Intent x=new Intent(MainActivity.this, Menu.class);
                    startActivity(x);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void irRegistrarse(View view){
        Intent intent = new Intent(this, frmRegister.class);
        startActivity(intent);
    }

}