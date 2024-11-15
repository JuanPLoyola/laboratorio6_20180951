package com.example.lab6;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_users_enabled) {
                selectedFragment = new UsuariosFragment(true); // Muestra usuarios habilitados
            } else if (itemId == R.id.nav_users_banned) {
                selectedFragment = new UsuariosFragment(false); // Muestra usuarios baneados
            } else if (itemId == R.id.nav_logout) {
                // Cerrar sesión
                FirebaseAuth.getInstance().signOut();
                finish(); // Terminar MainActivity y regresar a Login
                return true;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Cargar el fragmento de usuarios habilitados por defecto
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_users_enabled);
        }
    }
}


