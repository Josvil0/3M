package com.example.a3mdef;

import com.example.a3mdef.modelos.Book;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GoogleBooksResponse {

    @SerializedName("items")
    private List<Book> items;

    // Getter para obtener los libros
    public List<Book> getItems() {
        return items;
    }
}
