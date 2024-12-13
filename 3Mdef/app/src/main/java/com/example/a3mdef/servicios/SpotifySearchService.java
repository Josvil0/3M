package com.example.a3mdef.servicios;

import com.example.a3mdef.modelos.SpotifySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * The interface Spotify search service.
 */
public interface SpotifySearchService {
    /**
     * Search music call.
     *
     * @param authorization the authorization
     * @param type          the type
     * @param query         the query
     * @return the call
     */
    @GET("search")
    Call<SpotifySearchResponse> searchMusic(
            @Header("Authorization") String authorization,
            @Query("type") String type, // "artist,album,track" para buscar los tres
            @Query("q") String query
    );
}

