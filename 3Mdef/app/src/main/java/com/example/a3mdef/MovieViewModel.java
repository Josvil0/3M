package com.example.a3mdef;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a3mdef.modelos.Movie;
import com.example.a3mdef.modelos.MovieResponse;
import com.example.a3mdef.modelos.TMDbResponse;
import com.example.a3mdef.servicios.RetrofitClient;
import com.example.a3mdef.servicios.TMDbApiService;

//String apiKey = "38d68f606cbdd06f61403a6c6e40a548";

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel que gestiona los datos de películas obtenidos desde la API de TMDb.
 * Se encarga de realizar las búsquedas y almacenar los resultados o los errores.
 */
public class MovieViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();  // Lista mutable de películas
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();  // Mensaje de error mutable

    /**
     * Devuelve la lista de películas observada.
     *
     * @return Un objeto LiveData que contiene una lista de películas.
     */
    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    /**
     * Devuelve el mensaje de error observado.
     *
     * @return Un objeto LiveData que contiene el mensaje de error.
     */
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Establece la lista de películas.
     *
     * @param movies La lista de películas a establecer.
     */
    public void setMovies(List<Movie> movies) {
        this.movies.setValue(movies);  // Asigna los valores a la lista mutable
    }

    /**
     * Establece el mensaje de error.
     *
     * @param message El mensaje de error a establecer.
     */
    public void setErrorMessage(String message) {
        this.errorMessage.setValue(message);  // Asigna el valor del mensaje de error
    }

    /**
     * Realiza una búsqueda de películas a través de la API de TMDb.
     *
     * @param apiKey La clave API de TMDb para autenticar las solicitudes.
     * @param query  El texto de búsqueda de las películas.
     */
    public void fetchMovies(String apiKey, String query) {
        // Crear una instancia del servicio de la API utilizando Retrofit
        TMDbApiService apiService = RetrofitClient.getTMDbRetrofitInstance().create(TMDbApiService.class);

        // Hacer la solicitud para buscar películas
        Call<TMDbResponse> call = apiService.searchMovies(apiKey, query);

        // Ejecutar la llamada de forma asincrónica
        call.enqueue(new Callback<TMDbResponse>() {
            /**
             * Callback cuando la respuesta de la API es exitosa.
             *
             * @param call La llamada que se realizó.
             * @param response La respuesta obtenida de la API.
             */
            @Override
            public void onResponse(Call<TMDbResponse> call, Response<TMDbResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si la respuesta es exitosa, obtener la lista de películas
                    List<Movie> movieList = response.body().getResults();
                    setMovies(movieList);  // Establecer la lista de películas
                } else {
                    // Si hubo un error en la respuesta, establecer un mensaje de error
                    setErrorMessage("Error: " + response.code());
                }
            }

            /**
             * Callback cuando la solicitud falla.
             *
             * @param call La llamada que se realizó.
             * @param t El error que ocurrió durante la ejecución.
             */
            @Override
            public void onFailure(Call<TMDbResponse> call, Throwable t) {
                setErrorMessage("Failure: " + t.getMessage());  // Establecer mensaje de error
            }
        });
    }
}



