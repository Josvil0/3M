package com.example.a3mdef;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Cliente de Retrofit para interactuar con la API de TMDb (The Movie Database).
 * Esta clase se encarga de crear y proporcionar una instancia de Retrofit para realizar solicitudes HTTP.
 * La instancia se configura con la URL base de la API de TMDb y un convertidor para procesar las respuestas JSON.
 */
public class TMDbClient {

    // URL base para la API de TMDb
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    // Instancia de Retrofit (se inicializa solo una vez)
    private static Retrofit retrofit = null;

    /**
     * Obtiene la instancia de Retrofit para realizar las solicitudes a la API de TMDb.
     * Si la instancia de Retrofit ya ha sido creada, se devuelve la misma instancia.
     * Si no, se crea una nueva instancia de Retrofit configurada con la URL base y un convertidor de JSON.
     *
     * @return Una instancia de Retrofit configurada para hacer solicitudes a la API de TMDb.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Si la instancia aún no existe, la creamos.
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Configura la URL base de la API de TMDb.
                    .addConverterFactory(GsonConverterFactory.create())  // Utiliza Gson para convertir las respuestas JSON.
                    .build();  // Construye la instancia de Retrofit.
        }
        // Devuelve la instancia de Retrofit (nueva o reutilizada).
        return retrofit;
    }
}
