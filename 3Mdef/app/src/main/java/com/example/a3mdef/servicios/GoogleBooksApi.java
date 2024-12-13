package com.example.a3mdef.servicios;


import com.example.a3mdef.GoogleBooksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface Google books api.
 */
public interface GoogleBooksApi {

    /**
     * Search books call.
     *
     * @param query the query
     * @return the call
     */
// Endpoint para realizar la búsqueda de libros
    @GET("volumes")
    Call<GoogleBooksResponse> searchBooks(@Query("q") String query);
}



