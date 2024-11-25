package com.app.farmacia_fameza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.farmacia_fameza.view.ProductListFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.app.farmacia_fameza.databinding.ActivityMenuBinding;

public class Menu extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_category, R.id.nav_brand, R.id.nav_lote, R.id.nav_output, R.id.nav_kardex)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.logout) {
                // Lógica para logout (por ejemplo, cerrar sesión)
                performLogout();
                return true;
            }
            return NavigationUI.onNavDestinationSelected(item, navController);
        });
        // Listener para detectar el fragmento actual y actualizar el menú o íconos
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            invalidateOptionsMenu(); // Solicita una nueva preparación del menú
        });
    }

    private void performLogout() {
        // 1. Limpia credenciales locales
        SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // 2. Redirige a pantalla de inicio de sesión
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // 3. Mensaje de confirmación
        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {

        // Aquí puedes decidir qué hacer en función del fragmento actual
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        int currentFragmentId = navController.getCurrentDestination().getId();

        // Limpia el menú por defecto
        menu.clear();

        // Si estamos en el fragmento de productos o en la pantalla de inicio, agrega el ícono de "+"
        if (currentFragmentId == R.id.productListFragment || currentFragmentId == R.id.nav_home) {
            menu.add(0, R.id.add_product, 0, "Agregar Producto")
                    .setIcon(R.drawable.plus_sign) // Asegúrate de tener este drawable
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // Muestra en la acción si hay espacio
        }

        // Si estamos en el fragmento de categoría , inflar el menú original
        if (currentFragmentId == R.id.nav_category) {
            menu.add(0, R.id.add_category, 0, "Agregar Categoria")
                    .setIcon(R.drawable.plus_sign)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // Muestra en la acción si hay espacio
        }

        // Si estamos en el fragmento de categoría , inflar el menú original
        if (currentFragmentId == R.id.nav_brand) {
            menu.add(0, R.id.add_brand, 0, "Agregar Marca")
                    .setIcon(R.drawable.plus_sign)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // Muestra en la acción si hay espacio
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_category) {
            Bundle args = new Bundle();
            args.putBoolean("isEditMode", false);  // Indicador de modo "añadir"
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
            navController.navigate(R.id.categoryCrudFragment, args); // Reemplaza con el ID de tu nuevo fragmento
        }

        if (id == R.id.add_brand) {
            Bundle args = new Bundle();
            args.putBoolean("isEditMode", false);  // Indicador de modo "añadir"
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
            navController.navigate(R.id.brandCrudFragment, args); // Reemplaza con el ID de tu nuevo fragmento
        }

        // Verificar si se hizo clic en el ítem "Agregar Producto"
        if (id == R.id.add_product) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
            navController.navigate(R.id.addProductFragment); // Reemplaza con el ID de tu nuevo fragmento
            return true;
        }

        if (id == R.id.logout) {
            // Realiza las acciones de logout
            handleLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void handleLogout() {
        // Limpiar datos del usuario (SharedPreferences o similares)
        getSharedPreferences("USER_PREFS", MODE_PRIVATE).edit().clear().apply();

        // Mostrar un mensaje al usuario
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Redirigir al login
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Borra la pila de actividades
        startActivity(intent);

        // Finalizar la actividad actual
        finish();
    }
}