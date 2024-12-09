package com.example.a3mdef;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.SearchView;

import com.example.a3mdef.modelos.Album;
import com.example.a3mdef.modelos.Artist;
import com.example.a3mdef.modelos.SpotifySearchResponse;
import com.example.a3mdef.modelos.SpotifyToken;
import com.example.a3mdef.modelos.Track;
import com.example.a3mdef.servicios.RetrofitClient;
import com.example.a3mdef.servicios.SpotifyAuthService;
import com.example.a3mdef.servicios.SpotifySearchService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Fragmento para la búsqueda de música que se conecta a la API de Spotify y muestra los resultados.
public class SpotifyFragment extends Fragment {

    private SearchView searchView;  // SearchView donde el usuario ingresa el término de búsqueda
    private RecyclerView resultsRecyclerView;  // RecyclerView donde se mostrarán los resultados de la búsqueda
    private SpotifyAdapter adapter;  // Adaptador para mostrar los resultados en el RecyclerView

    // Constructor vacío, se utiliza para la creación del fragmento
    public SpotifyFragment() {
        // Constructor vacío
    }

    // Este método se llama cuando el fragmento se crea y se infla en la vista.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout
        View view = inflater.inflate(R.layout.fragment_music_search, container, false);

        // Inicializamos las vistas de búsqueda y RecyclerView
        searchView = view.findViewById(R.id.search_edit_text);  // Asigna el SearchView
        resultsRecyclerView = view.findViewById(R.id.results_recycler_view);  // Asigna el RecyclerView

        // Configuración del RecyclerView
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SpotifyAdapter();  // Inicializamos el adaptador
        resultsRecyclerView.setAdapter(adapter);  // Establecemos el adaptador al RecyclerView

        // Configuración para manejar la acción de búsqueda cuando el usuario envía la consulta
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Se ejecuta cuando se envía el texto de búsqueda
                if (!TextUtils.isEmpty(query)) {
                    // Si el término de búsqueda no está vacío, obtenemos el token de acceso para realizar la búsqueda
                    getAccessToken(query);
                } else {
                    // Si el término de búsqueda está vacío, mostramos un mensaje de advertencia
                    Toast.makeText(getContext(), "Por favor, ingrese un término de búsqueda", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // No estamos utilizando este método, pero se podría implementar búsqueda en tiempo real si se desea.
                return false;
            }
        });

        return view;
    }

    // Método para obtener el token de acceso de Spotify, necesario para hacer solicitudes a la API de Spotify
    private void getAccessToken(String query) {
        // Realiza una solicitud a la API de autenticación de Spotify usando Retrofit
        RetrofitClient.getSpotifyAuthRetrofitInstance().create(SpotifyAuthService.class)
                .getAccessToken("Basic ZTA1ODFmOGU2NGJiNDZmOTg5ZDE1NDkxYTFkODc5NTI6MjUzZTg2ZTdiNDViNGU3NWIzZTExZGU0NWNiMzU0ODQ=", "client_credentials")
                .enqueue(new Callback<SpotifyToken>() {
                    @Override
                    public void onResponse(Call<SpotifyToken> call, Response<SpotifyToken> response) {
                        // Si la respuesta es exitosa y el cuerpo de la respuesta no es nulo
                        if (response.isSuccessful() && response.body() != null) {
                            // Extrae el token de acceso de la respuesta
                            String accessToken = response.body().getAccess_token();
                            // Usa el token de acceso para realizar la búsqueda
                            performSearch(accessToken, query);
                        } else {
                            // Si ocurre un error al obtener el token, muestra un mensaje al usuario
                            Toast.makeText(getContext(), "Error al obtener el token", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SpotifyToken> call, Throwable t) {
                        // Si ocurre un error en la conexión, muestra un mensaje de error
                        Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para realizar la búsqueda en la API de Spotify usando el token de acceso
    private void performSearch(String accessToken, String query) {
        // Realiza la búsqueda de música (artistas, álbumes y canciones) usando Retrofit
        RetrofitClient.getSpotifyRetrofitInstance().create(SpotifySearchService.class)
                .searchMusic("Bearer " + accessToken, "artist,album,track", query)  // Pasamos el token y el término de búsqueda
                .enqueue(new Callback<SpotifySearchResponse>() {
                    @Override
                    public void onResponse(Call<SpotifySearchResponse> call, Response<SpotifySearchResponse> response) {
                        // Si la respuesta es exitosa y el cuerpo de la respuesta no es nulo
                        if (response.isSuccessful() && response.body() != null) {
                            // Obtiene la respuesta con los resultados de la búsqueda
                            SpotifySearchResponse searchResponse = response.body();

                            // Extrae las listas de artistas, álbumes y canciones de la respuesta
                            List<Artist> artists = searchResponse.getArtists().getItems();
                            List<Album> albums = searchResponse.getAlbums().getItems();
                            List<Track> tracks = searchResponse.getTracks().getItems();

                            // Actualiza los resultados en el RecyclerView usando el adaptador
                            adapter.setArtists(artists);  // Actualiza la lista de artistas
                            adapter.setAlbums(albums);    // Actualiza la lista de álbumes
                            adapter.setTracks(tracks);    // Actualiza la lista de canciones
                        } else {
                            // Si no se encontraron resultados, muestra un mensaje
                            Toast.makeText(getContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SpotifySearchResponse> call, Throwable t) {
                        // Si ocurre un error en la búsqueda, muestra un mensaje de error
                        Toast.makeText(getContext(), "Error en la búsqueda: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
