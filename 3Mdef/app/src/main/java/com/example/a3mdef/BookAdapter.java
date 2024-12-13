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
import com.example.a3mdef.modelos.BookInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter que gestiona la visualización de una lista de libros en un RecyclerView.
 * Cada libro se muestra con su título, autor y portada.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    // Lista de libros que se mostrarán
    private List<Book> books = new ArrayList<>();
    // Listener para manejar los clics sobre los elementos de la lista
    private OnItemClickListener listener;

    /**
     * Interface para manejar los clics sobre un libro.
     */
    public interface OnItemClickListener {
        /**
         * Método que se llama cuando un libro es seleccionado.
         *
         * @param book El libro que fue clickeado.
         */
        void onItemClick(Book book);
    }

    /**
     * Constructor vacío del adaptador.
     */
    public BookAdapter() {
        // Constructor vacío
    }

    /**
     * Establece el listener para los clics sobre los items.
     *
     * @param listener El listener a asignar.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Actualiza la lista de libros que se mostrarán en el RecyclerView.
     *
     * @param books Lista de libros a mostrar.
     */
    public void setBooks(List<Book> books) {
        if (books != null) {
            Log.d("BookAdapter", "Recibiendo " + books.size() + " libros.");
            this.books = books;
        } else {
            Log.d("BookAdapter", "Recibiendo lista vacía.");
            this.books = new ArrayList<>();
        }
        notifyDataSetChanged(); // Notifica que los datos han cambiado
    }

    /**
     * Infla el layout de cada item y lo convierte en un ViewHolder.
     * @param parent El contenedor de vistas en el que se insertará el item.
     * @param viewType El tipo de vista del item.
     * @return Un nuevo ViewHolder.
     */
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout 'item_book' para cada elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view); // Retornamos el nuevo ViewHolder con el item inflado
    }

    /**
     * Asocia los datos del libro con la vista correspondiente.
     * @param holder El ViewHolder que contiene las vistas del item.
     * @param position La posición del libro en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        // Obtenemos el libro en la posición actual
        Book book = books.get(position);
        // Obtenemos la información del libro (título, autores, imagen)
        BookInfo info = book.getVolumeInfo();

        // Establecemos el título del libro en el TextView correspondiente
        holder.bookTitle.setText(info.getTitle());
        // Establecemos los autores (si están disponibles), si no, mostramos "Autor desconocido"
        holder.bookAuthors.setText(info.getAuthors() != null && !info.getAuthors().isEmpty() ?
                String.join(", ", info.getAuthors()) : "Autor desconocido");

        // Establecemos la imagen del libro, buscando primero una imagen grande y luego una más pequeña si no está disponible
        String imageUrl = null;
        if (info.getImageLinks() != null) {
            imageUrl = info.getImageLinks().getLarge();
            if (imageUrl == null) {
                imageUrl = info.getImageLinks().getMedium();
            }
            if (imageUrl == null) {
                imageUrl = info.getImageLinks().getSmall();
            }
            if (imageUrl == null) {
                imageUrl = info.getImageLinks().getThumbnail();
            }
        }

        // Usamos Glide para cargar la imagen en el ImageView del libro
        if (imageUrl != null) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl) // Cargamos la URL de la imagen
                    .into(holder.bookImage); // Establecemos la imagen en el ImageView
        }

        // Detectamos el clic en el item del RecyclerView
        holder.itemView.setOnClickListener(v -> {
            // Si el listener no es null, se llama al método onItemClick con el libro seleccionado
            if (listener != null) {
                listener.onItemClick(book);
            }
        });
    }

    /**
     * Retorna el número de elementos en la lista de libros.
     * @return El número de libros.
     */
    @Override
    public int getItemCount() {
        return books != null ? books.size() : 0;
    }

    /**
     * ViewHolder que contiene las vistas del item en el RecyclerView.
     */
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Book title.
         */
// Los componentes que forman cada item: título, autores e imagen del libro
        TextView bookTitle, /**
         * The Book authors.
         */
        bookAuthors;
        /**
         * The Book image.
         */
        ImageView bookImage;

        /**
         * Constructor del ViewHolder.
         *
         * @param itemView La vista que contiene el item.
         */
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            // Asignamos los componentes a las vistas correspondientes en el layout
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthors = itemView.findViewById(R.id.bookAuthors);
            bookImage = itemView.findViewById(R.id.bookCoverImageView);
        }
    }
}
