package com.app.farmacia_fameza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.farmacia_fameza.models.request.AuthRequest;
import com.app.farmacia_fameza.models.response.ApiResponse;
import com.app.farmacia_fameza.services.IAuthService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button btnLogin;
    public IAuthService iAuthService;

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


        // Configurar el logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Crear el cliente OkHttp y agregar el interceptor
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        // Crear Retrofit con el cliente OkHttp
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.apifarmaciafameza.somee.com/api/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iAuthService = retrofit.create(IAuthService.class);

        txtEmail = findViewById(R.id.txtNombreUsuario);
        txtPassword = findViewById(R.id.txtContrasenia);
    }

    public void isAuthenticated(View view){
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(email);
        authRequest.setPassword(password);

        Call<ApiResponse> call = iAuthService.login(authRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (!response.isSuccessful()) {

                    if(response.code()==404){
                        Toast.makeText(MainActivity.this, "Error " + response.code() + ": No existe registro de un usuario con ese email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(response.code()==401){
                        Toast.makeText(MainActivity.this, "Error " + response.code() + ": El usuario se encuentra sin autorizacion de acceso", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                ApiResponse apiResponse = response.body();

                /*if (apiResponse == null) {
                    Toast.makeText(MainActivity.this, "El cuerpo de la respuesta es nulo", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (apiResponse.getStatusCode() == 404) {
                    Toast.makeText(MainActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!apiResponse.isSuccess()) {
                    Toast.makeText(MainActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }*/

                Toast.makeText(MainActivity.this, "Welcome " + apiResponse.getData().getFirstName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Inicio.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                // Error en la conexión
                Toast.makeText(MainActivity.this, "Error en la conexión"+call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}