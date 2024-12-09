package com.example.a3mdef.servicios;

import android.util.Base64;
import android.util.Log;

import com.example.a3mdef.modelos.SpotifyToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/";
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1/";
    private static final String SPOTIFY_AUTH_BASE_URL = "https://accounts.spotify.com/";

    private static Retrofit googleBooksRetrofit;
    private static Retrofit tmdbRetrofit;
    private static Retrofit spotifyRetrofit;
    private static Retrofit spotifyAuthRetrofit;

    // Retrofit para Google Books API
    public static Retrofit getGoogleBooksRetrofitInstance() {
        if (googleBooksRetrofit == null) {
            googleBooksRetrofit = new Retrofit.Builder()
                    .baseUrl(GOOGLE_BOOKS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return googleBooksRetrofit;
    }

    // Retrofit para TMDb API
    public static Retrofit getTMDbRetrofitInstance() {
        if (tmdbRetrofit == null) {
            tmdbRetrofit = new Retrofit.Builder()
                    .baseUrl(TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return tmdbRetrofit;
    }


    // Retrofit para Spotify Web API
    public static Retrofit getSpotifyRetrofitInstance() {
        if (spotifyRetrofit == null) {
            spotifyRetrofit = new Retrofit.Builder()
                    .baseUrl(SPOTIFY_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return spotifyRetrofit;
    }

    public static Retrofit getSpotifyAuthRetrofitInstance() {
        if (spotifyAuthRetrofit == null) {
            spotifyAuthRetrofit = new Retrofit.Builder()
                    .baseUrl(SPOTIFY_AUTH_BASE_URL)  // Base URL para autenticación
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return spotifyAuthRetrofit;
    }

        // Método para obtener el access token de Spotify
        public void getAccessToken() {
            // Token base64 que ya tienes, ejemplo: "Basic <base64(client_id:client_secret)>"
            String authorizationHeader = "Basic ZTA1ODFmOGU2NGJiNDZmOTg5ZDE1NDkxYTFkODc5NTI6MjUzZTg2ZTdiNDViNGU3NWIzZTExZGU0NWNiMzU0ODQ=";

            // Configurar Retrofit
            Retrofit retrofit = RetrofitClient.getSpotifyAuthRetrofitInstance();
            SpotifyAuthService authService = retrofit.create(SpotifyAuthService.class);

            // Realizar la solicitud
            Call<SpotifyToken> call = authService.getAccessToken(
                    authorizationHeader,   // Usar el token codificado
                    "client_credentials"   // Grant type
            );

            call.enqueue(new Callback<SpotifyToken>() {
                @Override
                public void onResponse(Call<SpotifyToken> call, Response<SpotifyToken> response) {
                    if (response.isSuccessful()) {
                        SpotifyToken token = response.body();
                        if (token != null) {
                            String accessToken = token.getAccess_token();
                            Log.d("Spotify", "Access Token: " + accessToken);
                            // Usar el token para hacer llamadas a la API de Spotify
                        } else {
                            Log.e("Spotify", "Token response is null");
                        }
                    } else {
                        Log.e("Spotify", "Error al obtener el token: " + response.code());
                    }
                }


                @Override
                public void onFailure(Call<SpotifyToken> call, Throwable t) {
                    Log.e("Spotify", "Fallo en la conexión: " + t.getMessage());
                }
            });
        }


    // Interfaz de callback para recibir el token
        public interface TokenCallback {
            void onTokenReceived(String accessToken);
            void onError(String error);
        }
    }

