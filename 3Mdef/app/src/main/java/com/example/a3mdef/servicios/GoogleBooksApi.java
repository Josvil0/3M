package com.example.a3mdef.servicios;


import com.example.a3mdef.GoogleBooksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApi {

    // Endpoint para realizar la búsqueda de libros
    @GET("volumes")
    Call<GoogleBooksResponse> searchBooks(@Query("q") String query);
}



