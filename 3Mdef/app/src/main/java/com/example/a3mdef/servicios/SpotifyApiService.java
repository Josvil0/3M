package com.example.a3mdef.servicios;

import com.example.a3mdef.modelos.SpotifySearchResponse;
import com.example.a3mdef.modelos.SpotifyToken;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


    public interface SpotifyApiService {

        // Buscar artistas, álbumes y canciones
    @GET("v1/search")
    Call<SpotifySearchResponse> search(
            @Header("Authorization") String authorization,
            @Query("q") String query,
            @Query("type") String type,
            @Query("limit") int limit
    );

    }




