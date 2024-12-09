package com.example.a3mdef;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.SearchView;

import com.example.a3mdef.modelos.Movie;  // Modelo que representa una película.
import com.example.a3mdef.modelos.TMDbResponse;  // Respuesta del API de TMDb.
import com.example.a3mdef.servicios.RetrofitClient;  // Cliente Retrofit para hacer las solicitudes HTTP.
import com.example.a3mdef.servicios.TMDbApiService;  // Servicio para interactuar con la API de TMDb.

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SearchView searchView;

    private static final String TMDB_API_KEY = "38d68f606cbdd06f61403a6c6e40a548";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragmento.
        View view = inflater.inflate(R.layout.layout_movies, container, false);

        // Inicializa las vistas
        recyclerView = view.findViewById(R.id.recyclerViewMovies);
        searchView = view.findViewById(R.id.searchViewMovies);

        // Verifica si las vistas fueron correctamente inicializadas
        if (recyclerView == null || searchView == null) {
            Log.e("MoviesFragment", "Error: Las vistas no se han inicializado correctamente.");
            return view;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));  // Configura el layout manager para el RecyclerView.

        // Configura la búsqueda en el SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Cuando se envía la consulta, se realiza la búsqueda.
                searchMovies(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;  // Devuelve la vista inflada.
    }

    // Método que realiza la búsqueda de películas.
    private void searchMovies(String query) {
        // Verifica que la consulta no esté vacía.
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, introduce un término de búsqueda.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crea una instancia de TMDbApiService para realizar la solicitud de búsqueda.
        TMDbApiService api = RetrofitClient.getTMDbRetrofitInstance()
                .create(TMDbApiService.class);

        // Llama al método `searchMovies` de la API con la clave de la API y la consulta.
        Call<TMDbResponse> call = api.searchMovies(TMDB_API_KEY, query);

        // Envia la solicitud asincrónica.
        call.enqueue(new Callback<TMDbResponse>() {
            @Override
            public void onResponse(Call<TMDbResponse> call, Response<TMDbResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si la respuesta es exitosa y contiene datos:
                    List<Movie> movies = response.body().getResults();
                    if (movies != null && !movies.isEmpty()) {
                        // Si hay resultados, los mostramos en el RecyclerView.
                        for (Movie movie : movies) {
                            Log.d("MoviesFragment", "Poster Path: " + movie.getPosterPath());  // Log para depuración.
                        }
                        movieAdapter = new MovieAdapter(movies);  // Inicializamos el adaptador con los resultados.
                        recyclerView.setAdapter(movieAdapter);  // Configuramos el RecyclerView con el adaptador.
                    } else {
                        // Si no se encontraron películas, mostramos un mensaje.
                        Toast.makeText(getContext(), "No se encontraron películas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si la respuesta fue incorrecta, mostramos un error.
                    Toast.makeText(getContext(), "Error al buscar películas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TMDbResponse> call, Throwable t) {
                // Si ocurre un error en la solicitud (problema de red, etc.), mostramos un mensaje de error.
                Log.e("MoviesFragment", t.getMessage());
                Toast.makeText(getContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
