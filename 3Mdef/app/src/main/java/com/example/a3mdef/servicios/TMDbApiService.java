package com.example.a3mdef.servicios;

import com.example.a3mdef.modelos.TMDbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface Tm db api service.
 */
public interface TMDbApiService {

    /**
     * Search movies call.
     *
     * @param apiKey the api key
     * @param query  the query
     * @return the call
     */
    @GET("search/movie")
    Call<TMDbResponse> searchMovies(@Query("api_key") String apiKey, @Query("query") String query);
}
