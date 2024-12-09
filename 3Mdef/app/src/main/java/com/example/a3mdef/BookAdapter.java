package com.example.a3mdef;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a3mdef.modelos.Book;

import java.util.List;

// Adaptador para mostrar una lista de libros en un RecyclerView
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;  // Lista que contiene los libros

    // Constructor que recibe la lista de libros
    public BookAdapter(List<Book> books) {
        this.books = books;
    }

    // Método que infla el layout del item y devuelve un ViewHolder
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout item_book para cada item del RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);  // Crear y devolver un ViewHolder con el layout inflado
    }

    // Método que asigna los datos de un libro a las vistas de cada item
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        // Obtener el libro de la lista en la posición actual
        Book book = books.get(position);

        // Asignar el título del libro al TextView correspondiente
        holder.titleTextView.setText(book.getVolumeInfo().getTitle());

        // Obtener y mostrar el primer autor del libro
        List<String> authors = book.getVolumeInfo().getAuthors();
        if (authors != null && !authors.isEmpty()) {
            holder.authorTextView.setText(authors.get(0));  // Mostrar solo el primer autor
        } else {
            holder.authorTextView.setText("Autor desconocido");  // Si no hay autor, mostrar mensaje por defecto
        }

        // Obtener la URL de la imagen del libro (portada)
        String imageUrl = null;
        if (book.getVolumeInfo().getImageLinks() != null) {
            imageUrl = book.getVolumeInfo().getImageLinks().getThumbnail();  // Obtener la URL de la imagen en miniatura
        }

        // Log para depuración, muestra la URL de la imagen
        Log.d("BookAdapter", "Image URL: " + imageUrl);

        // Usar Glide para cargar la imagen en el ImageView
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Si la URL es válida, cargar la imagen con Glide y redimensionarla
            Glide.with(holder.bookImageView.getContext())
                    .load(imageUrl)
                    .override(150, 200)  // Establecer el tamaño de la imagen
                    .into(holder.bookImageView);  // Cargar la imagen en el ImageView
        } else {
            // Si no hay URL de imagen, mostrar una imagen por defecto
            holder.bookImageView.setImageResource(R.drawable.default_image);
        }
    }

    // Método que devuelve el número total de items (libros) en la lista
    @Override
    public int getItemCount() {
        return books.size();  // Número total de libros en la lista
    }

    // Clase ViewHolder para contener las vistas de cada item
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;     // Vista para mostrar el título del libro
        TextView authorTextView;    // Vista para mostrar el autor del libro
        ImageView bookImageView;    // Vista para mostrar la imagen de la portada del libro

        // Constructor que inicializa las vistas del item
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);  // Asignar la vista del título
            authorTextView = itemView.findViewById(R.id.authorTextView);  // Asignar la vista del autor
            bookImageView = itemView.findViewById(R.id.bookImageView);    // Asignar la vista de la imagen del libro
        }
    }
}
