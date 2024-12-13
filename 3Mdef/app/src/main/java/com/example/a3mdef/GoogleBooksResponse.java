package com.example.a3mdef;

import com.example.a3mdef.modelos.Book;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Representa la respuesta de la API de Google Books al realizar una búsqueda de libros.
 * Contiene una lista de libros obtenidos como resultado de la consulta.
 */
public class GoogleBooksResponse {

    // Lista de libros obtenidos de la respuesta de la API
    @SerializedName("items")
    private List<Book> items;

    /**
     * Obtiene la lista de libros obtenidos desde la API.
     *
     * @return La lista de libros.
     */
    public List<Book> getItems() {
        return items;
    }

    /**
     * Convierte la respuesta a una cadena de texto representando la lista de libros.
     * Este método es útil para depuración y para visualizar los detalles de los libros en la respuesta.
     * @return Una cadena con los detalles de los libros.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (items != null) {
            for (Book book : items) {
                sb.append(book.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
