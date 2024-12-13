package com.example.a3mdef;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a3mdef.modelos.Book;
import com.example.a3mdef.servicios.GoogleBooksApi;
import com.example.a3mdef.servicios.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * ViewModel encargado de gestionar la lógica para obtener y almacenar los libros desde la API de Google Books.
 * Proporciona los datos a los fragmentos y actividades que lo observen.
 */
public class BookViewModel extends ViewModel {

    // URL base para la API de Google Books
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/";

    // MutableLiveData para almacenar la lista de libros
    private MutableLiveData<List<Book>> books = new MutableLiveData<>();

    /**
     * Obtiene un objeto LiveData que contiene la lista de libros.
     * Los fragmentos y actividades pueden observar este LiveData para recibir actualizaciones cuando la lista cambie.
     *
     * @return LiveData que contiene la lista de libros.
     */
    public LiveData<List<Book>> getBooks() {
        return books;
    }

    /**
     * Realiza la búsqueda de libros en la API de Google Books utilizando un término de búsqueda.
     * Una vez obtenida la respuesta, actualiza la lista de libros.
     *
     * @param query El término de búsqueda para encontrar libros.
     */
    public void searchBooks(String query) {
        // Crear una instancia de Retrofit para realizar la solicitud
        Retrofit retrofit = RetrofitClient.getGoogleBooksRetrofitInstance();
        GoogleBooksApi api = retrofit.create(GoogleBooksApi.class);

        // Llamada a la API de Google Books
        Call<GoogleBooksResponse> call = api.searchBooks(query);
        call.enqueue(new Callback<GoogleBooksResponse>() {
            @Override
            public void onResponse(Call<GoogleBooksResponse> call, Response<GoogleBooksResponse> response) {
                try {
                    // Verificar si la respuesta es exitosa
                    if (response.isSuccessful() && response.body() != null) {
                        List<Book> bookList = response.body().getItems();
                        if (bookList != null && !bookList.isEmpty()) {
                            // Si se obtienen libros, actualizar la lista en LiveData
                            Log.d("BookViewModel", "Libros recibidos: " + bookList.size());
                            books.setValue(bookList);
                        } else {
                            // Si no se encuentran libros, establecer una lista vacía
                            Log.d("BookViewModel", "No se encontraron libros.");
                            books.setValue(new ArrayList<>());
                        }
                    } else {
                        // Si la respuesta no es exitosa, establecer una lista vacía
                        Log.e("BookViewModel", "Error en la respuesta: " + response.message());
                        books.setValue(new ArrayList<>());
                    }
                } catch (Exception e) {
                    // Manejo de excepciones al procesar la respuesta
                    Log.e("BookViewModel", "Error procesando la respuesta: " + e.getMessage());
                    books.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<GoogleBooksResponse> call, Throwable t) {
                // Manejo de fallo en la solicitud
                Log.e("BookViewModel", "Error al realizar la solicitud: " + t.getMessage());
            }
        });
    }
}
