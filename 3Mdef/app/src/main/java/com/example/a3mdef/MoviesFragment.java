package com.example.a3mdef;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.SearchView;

import com.example.a3mdef.modelos.Movie;

import java.util.List;

/**
 * Fragmento que muestra una lista de películas obtenidas desde la API, utilizando un RecyclerView.
 * Permite realizar búsquedas de películas a través de un SearchView.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView recyclerView;  // Vista para mostrar las películas en una lista
    private MovieAdapter movieAdapter;  // Adaptador para el RecyclerView
    private SearchView searchView;  // Vista de búsqueda para filtrar las películas
    private MovieViewModel movieViewModel;  // ViewModel para gestionar los datos de las películas

    /**
     * Inflar la vista del fragmento y configurar los elementos de la interfaz.
     *
     * @param inflater El inflador de la vista.
     * @param container El contenedor en el que se añadirá la vista.
     * @param savedInstanceState El estado guardado del fragmento (si existe).
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.layout_movies, container, false);

        // Inicializar las vistas
        recyclerView = view.findViewById(R.id.recyclerViewMovies);
        searchView = view.findViewById(R.id.searchViewMovies);

        // Configurar el RecyclerView para usar un LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener una instancia del ViewModel
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Observar cambios en la lista de películas
        movieViewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && !movies.isEmpty()) {
                // Crear el adaptador con la lista de películas y un listener para manejar los clics
                movieAdapter = new MovieAdapter(movies, movie -> {
                    // Crear el fragmento de detalle de la película
                    MovieDetailFragment detailFragment = new MovieDetailFragment();

                    // Pasar los datos de la película seleccionada al fragmento de detalle
                    Bundle args = new Bundle();
                    args.putSerializable("movie", movie);  // Guardar el objeto Movie
                    detailFragment.setArguments(args);

                    // Reemplazar el fragmento actual con el detalle
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, detailFragment)
                            .addToBackStack(null)  // Permitir volver atrás
                            .commit();
                });

                // Configurar el adaptador en el RecyclerView
                recyclerView.setAdapter(movieAdapter);
            }
        });

        // Observar errores en el ViewModel y mostrar un mensaje si ocurre un error
        movieViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                // Mostrar un mensaje de error en un Toast
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el SearchView para realizar búsquedas de películas
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Cuando el usuario envía un texto de búsqueda, se realiza la búsqueda de películas.
             *
             * @param query El texto ingresado por el usuario.
             * @return false para permitir la acción predeterminada.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                String apiKey = "38d68f606cbdd06f61403a6c6e40a548";  // Clave de API para realizar la búsqueda
                movieViewModel.fetchMovies(apiKey, query);  // Llamar al método en el ViewModel para obtener las películas
                return false;
            }

            /**
             * Cuando el texto en el SearchView cambia, se realiza una búsqueda de películas si la longitud
             * del texto es mayor que 2 caracteres.
             *
             * @param newText El texto que el usuario está escribiendo.
             * @return false para permitir la acción predeterminada.
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    String apiKey = "38d68f606cbdd06f61403a6c6e40a548";  // Clave de API para realizar la búsqueda
                    movieViewModel.fetchMovies(apiKey, newText);  // Llamar al método en el ViewModel para obtener las películas
                }
                return false;
            }
        });

        return view;  // Retornar la vista inflada
    }
}

