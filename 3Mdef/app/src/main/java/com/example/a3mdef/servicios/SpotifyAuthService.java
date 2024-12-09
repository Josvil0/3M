package com.example.a3mdef.servicios;

import com.example.a3mdef.modelos.SpotifyToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SpotifyAuthService {
    @POST("api/token")
    @FormUrlEncoded
    Call<SpotifyToken> getAccessToken(
            @Header("Authorization") String authorizationHeader,
            @Field("grant_type") String grantType
    );
}