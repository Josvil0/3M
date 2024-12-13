package com.example.a3mdef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a3mdef.modelos.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter para el RecyclerView que gestiona la visualización de una lista de películas.
 * Esta clase es responsable de crear las vistas para cada elemento en la lista, enlazarlas con los datos
 * de las películas y manejar los clics en los ítems.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;  // Lista de películas a mostrar
    private final OnItemClickListener listener;  // Interfaz para manejar el clic en un ítem

    /**
     * Interfaz que define el comportamiento al hacer clic en un ítem de la lista.
     */
    public interface OnItemClickListener {
        /**
         * Método llamado cuando un ítem de la lista es clickeado.
         *
         * @param movie La película clickeada.
         */
        void onItemClick(Movie movie);
    }

    /**
     * Constructor del adaptador.
     *
     * @param movies   Lista de películas que se mostrarán.
     * @param listener Listener que maneja el clic en un ítem.
     */
    public MovieAdapter(List<Movie> movies, OnItemClickListener listener) {
        this.movies = movies != null ? movies : new ArrayList<>();  // Asegura que la lista no sea null
        this.listener = listener;
    }

    /**
     * Actualiza la lista de películas a mostrar en el RecyclerView.
     *
     * @param newMovies Nueva lista de películas.
     */
    public void setMovies(List<Movie> newMovies) {
        this.movies = newMovies != null ? newMovies : new ArrayList<>();
        notifyDataSetChanged();  // Notifica al RecyclerView que los datos han cambiado
    }

    /**
     * Crea y devuelve una nueva instancia de ViewHolder.
     * Este método infla el layout para cada ítem de la lista.
     *
     * @param parent El contenedor donde se va a agregar la vista.
     * @param viewType Tipo de vista (en este caso, no se usa, ya que hay solo un tipo de ítem).
     * @return Un nuevo ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tmdb, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asocia los datos de una película a las vistas de un ítem.
     * Este método es llamado cuando el RecyclerView necesita mostrar un ítem en pantalla.
     *
     * @param holder El ViewHolder que contiene las vistas.
     * @param position La posición del ítem en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);  // Obtiene la película en la posición correspondiente

        // Asigna el título de la película al TextView
        holder.titleTextView.setText(movie.getTitle());

        // Carga la imagen del póster usando Glide
        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.default_image)  // Imagen por defecto mientras se carga
                .into(holder.posterImageView);

        // Configura el listener para manejar el clic en el ítem
        holder.itemView.setOnClickListener(v -> listener.onItemClick(movie));
    }

    /**
     * Devuelve la cantidad de ítems en la lista de películas.
     *
     * @return El número de ítems en la lista.
     */
    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;  // Devuelve el tamaño de la lista
    }

    /**
     * ViewHolder que contiene las vistas de un ítem de la lista.
     * Este ViewHolder mantiene las referencias a los elementos de la interfaz.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Title text view.
         */
        TextView titleTextView;  // Vista para mostrar el título de la película
        /**
         * The Poster image view.
         */
        ImageView posterImageView;  // Vista para mostrar la imagen del póster

        /**
         * Constructor del ViewHolder.
         *
         * @param itemView La vista del ítem (el layout inflado para este ViewHolder).
         */
        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}
