package com.example.a3mdef;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3mdef.modelos.Book;
import com.example.a3mdef.servicios.GoogleBooksApi;
import com.example.a3mdef.servicios.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment {

    private RecyclerView recyclerView;  // RecyclerView para mostrar los libros
    private BookAdapter bookAdapter;  // Adaptador para los libros
    private androidx.appcompat.widget.SearchView searchView;  // Barra de búsqueda para filtrar libros

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        // Inicializar RecyclerView y SearchView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));  // Configura el LayoutManager del RecyclerView

        searchView = view.findViewById(R.id.searchView);  // Asigna la barra de búsqueda al SearchView
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Se ejecuta cuando se envía el texto de búsqueda
                searchBooks(query);  // Llamar a la función de búsqueda con el término ingresado
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Puedes implementar búsqueda en tiempo real si es necesario
                return false;
            }
        });

        return view;  // Retorna la vista inflada
    }

    // Método para realizar la búsqueda de libros a través de la API de Google Books
    private void searchBooks(String query) {
        // Verificar si el término de búsqueda está vacío
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Introduce un término de búsqueda", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear una instancia del servicio de Google Books API
        GoogleBooksApi api = RetrofitClient.getGoogleBooksRetrofitInstance().create(GoogleBooksApi.class);
        // Llamar a la API de Google Books con el término de búsqueda
        Call<GoogleBooksResponse> call = api.searchBooks(query);

        // Realizar la llamada asíncrona a la API
        call.enqueue(new Callback<GoogleBooksResponse>() {
            @Override
            public void onResponse(Call<GoogleBooksResponse> call, Response<GoogleBooksResponse> response) {
                // Manejar la respuesta exitosa de la API
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body().getItems();  // Obtener los libros de la respuesta
                    if (books != null && !books.isEmpty()) {
                        // Si se encuentran libros, configurar el adaptador
                        bookAdapter = new BookAdapter(books);
                        recyclerView.setAdapter(bookAdapter);  // Asignar el adaptador al RecyclerView
                    } else {
                        // Si no se encuentran libros, mostrar un mensaje
                        Toast.makeText(getContext(), "No se encontraron libros", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si la respuesta de la API no es exitosa
                    Toast.makeText(getContext(), "Error al buscar libros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GoogleBooksResponse> call, Throwable t) {
                // Manejar error de conexión
                Log.e("API Error", t.getMessage());  // Loguear el error
                Toast.makeText(getContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
