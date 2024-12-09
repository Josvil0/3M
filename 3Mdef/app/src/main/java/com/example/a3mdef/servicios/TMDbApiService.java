package com.example.a3mdef.servicios;

import com.example.a3mdef.modelos.TMDbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbApiService {
    @GET("search/movie")
    Call<TMDbResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );
}
