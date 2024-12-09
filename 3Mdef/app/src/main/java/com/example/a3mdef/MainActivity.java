package com.example.a3mdef;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el BottomNavigationView
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigation);

        // Cargar el fragmento predeterminado (BooksFragment) si es la primera vez
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new BooksFragment()) // Fragment predeterminado
                    .commit();
        }

        // Manejar la selección en el BottomNavigationView
        navigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Verificar los IDs de los elementos seleccionados usando 'if' en lugar de 'switch'
            if (item.getItemId() == R.id.libros) {  // Asegúrate de que estos IDs coincidan con los del archivo de menú
                selectedFragment = new BooksFragment();
            } else if (item.getItemId() == R.id.peliculas) {
                selectedFragment = new MoviesFragment();
            }else if (item.getItemId() == R.id.Musica) {
                selectedFragment = new SpotifyFragment();
            }

            // Reemplazar el fragmento solo si uno ha sido seleccionado
            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
}
