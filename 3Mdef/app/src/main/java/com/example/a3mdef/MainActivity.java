package com.example.a3mdef;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Actividad principal que gestiona la interfaz de usuario y la navegación entre fragmentos en la aplicación.
 * Utiliza un BottomNavigationView para permitir a los usuarios navegar entre diferentes secciones (libros, películas y música).
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Método que se llama cuando se crea la actividad.
     * Inicializa el BottomNavigationView y configura la navegación entre fragmentos.
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);  // Establece el tema de la actividad
        setContentView(R.layout.activity_main);  // Establece el layout de la actividad

        // Inicializa el BottomNavigationView
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigation);

        // Cargar el fragmento predeterminado (BooksFragment) si es la primera vez que se crea la actividad
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new BooksFragment())  // Fragment predeterminado
                    .commit();
        }

        // Configura el listener para manejar la selección de elementos en el BottomNavigationView
        navigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Verificar los IDs de los elementos seleccionados usando 'if' en lugar de 'switch'
            if (item.getItemId() == R.id.libros) {  // ID para la sección de libros
                selectedFragment = new BooksFragment();
            } else if (item.getItemId() == R.id.peliculas) {  // ID para la sección de películas
                selectedFragment = new MoviesFragment();
            } else if (item.getItemId() == R.id.Musica) {  // ID para la sección de música
                selectedFragment = new SpotifyFragment();
            }

            // Reemplazar el fragmento solo si uno ha sido seleccionado
            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)  // Reemplaza el fragmento actual
                        .commit();
            }

            return true;  // Indica que el ítem fue seleccionado correctamente
        });
    }
}
