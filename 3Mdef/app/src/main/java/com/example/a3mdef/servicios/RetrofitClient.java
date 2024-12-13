package com.example.a3mdef.servicios;

import android.util.Base64;
import android.util.Log;

import com.example.a3mdef.modelos.SpotifyToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Cliente Retrofit para interactuar con múltiples APIs (Google Books, TMDb y Spotify).
 * Esta clase proporciona las instancias necesarias de Retrofit para realizar solicitudes HTTP
 * a las APIs de Google Books, TMDb y Spotify.
 */
public class RetrofitClient {

    // URLs base para cada API
    private static final String GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/";
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1/";
    private static final String SPOTIFY_AUTH_BASE_URL = "https://accounts.spotify.com/";

    // Instancias de Retrofit para cada API
    private static Retrofit googleBooksRetrofit;
    private static Retrofit tmdbRetrofit;
    private static Retrofit spotifyRetrofit;
    private static Retrofit spotifyAuthRetrofit;

    /**
     * Obtiene la instancia de Retrofit para la API de Google Books.
     *
     * @return La instancia de Retrofit para Google Books API.
     */
    public static Retrofit getGoogleBooksRetrofitInstance() {
        if (googleBooksRetrofit == null) {
            googleBooksRetrofit = new Retrofit.Builder()
                    .baseUrl(GOOGLE_BOOKS_BASE_URL)  // Configura la URL base de Google Books API.
                    .addConverterFactory(GsonConverterFactory.create())  // Convierte la respuesta JSON.
                    .build();
        }
        return googleBooksRetrofit;
    }

    /**
     * Obtiene la instancia de Retrofit para la API de TMDb.
     *
     * @return La instancia de Retrofit para TMDb API.
     */
    public static Retrofit getTMDbRetrofitInstance() {
        if (tmdbRetrofit == null) {
            tmdbRetrofit = new Retrofit.Builder()
                    .baseUrl(TMDB_BASE_URL)  // Configura la URL base de TMDb API.
                    .addConverterFactory(GsonConverterFactory.create())  // Convierte la respuesta JSON.
                    .build();
        }
        return tmdbRetrofit;
    }

    /**
     * Obtiene la instancia de Retrofit para la API de Spotify.
     *
     * @return La instancia de Retrofit para Spotify Web API.
     */
    public static Retrofit getSpotifyRetrofitInstance() {
        if (spotifyRetrofit == null) {
            spotifyRetrofit = new Retrofit.Builder()
                    .baseUrl(SPOTIFY_BASE_URL)  // Configura la URL base de Spotify API.
                    .addConverterFactory(GsonConverterFactory.create())  // Convierte la respuesta JSON.
                    .build();
        }
        return spotifyRetrofit;
    }

    /**
     * Obtiene la instancia de Retrofit para la API de autenticación de Spotify.
     *
     * @return La instancia de Retrofit para la autenticación de Spotify.
     */
    public static Retrofit getSpotifyAuthRetrofitInstance() {
        if (spotifyAuthRetrofit == null) {
            spotifyAuthRetrofit = new Retrofit.Builder()
                    .baseUrl(SPOTIFY_AUTH_BASE_URL)  // URL base para autenticación.
                    .addConverterFactory(GsonConverterFactory.create())  // Convierte la respuesta JSON.
                    .build();
        }
        return spotifyAuthRetrofit;
    }

    /**
     * Método para obtener el access token de Spotify. Se usa para autenticarse con la API de Spotify.
     * Realiza una solicitud HTTP para obtener el token utilizando el flujo de credenciales de cliente.
     */
    public void getAccessToken() {
        // Token codificado en base64 para la autenticación en Spotify.
        String authorizationHeader = "Basic ZTA1ODFmOGU2NGJiNDZmOTg5ZDE1NDkxYTFkODc5NTI6MjUzZTg2ZTdiNDViNGU3NWIzZTExZGU0NWNiMzU0ODQ=";

        // Configurar Retrofit para la autenticación de Spotify.
        Retrofit retrofit = RetrofitClient.getSpotifyAuthRetrofitInstance();
        SpotifyAuthService authService = retrofit.create(SpotifyAuthService.class);

        // Crear la llamada para obtener el access token.
        Call<SpotifyToken> call = authService.getAccessToken(
                authorizationHeader,   // Token base64 de autenticación
                "client_credentials"   // Tipo de grant
        );

        // Enviar la solicitud de forma asíncrona.
        call.enqueue(new Callback<SpotifyToken>() {
            @Override
            public void onResponse(Call<SpotifyToken> call, Response<SpotifyToken> response) {
                if (response.isSuccessful()) {
                    SpotifyToken token = response.body();
                    if (token != null) {
                        String accessToken = token.getAccess_token();  // Obtener el token de acceso.
                        Log.d("Spotify", "Access Token: " + accessToken);
                        // Usar el token para realizar solicitudes a la API de Spotify.
                    } else {
                        Log.e("Spotify", "La respuesta del token es nula");
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

    /**
     * Interfaz para recibir el token de acceso de Spotify.
     * Implementa métodos para manejar el éxito y el error en la obtención del token.
     */
    public interface TokenCallback {
        /**
         * Método llamado cuando se recibe el token de acceso exitosamente.
         *
         * @param accessToken El token de acceso de Spotify.
         */
        void onTokenReceived(String accessToken);

        /**
         * Método llamado cuando ocurre un error al obtener el token de acceso.
         *
         * @param error El mensaje de error.
         */
        void onError(String error);
    }
}
