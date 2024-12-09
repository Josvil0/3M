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

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;

    // Constructor que inicializa la lista de películas.
    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    // Crea el ViewHolder para cada elemento del RecyclerView.
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout del item (cada película) para ser usado en el RecyclerView.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);  // Devuelve el ViewHolder con el layout inflado.
    }

    // Asocia los datos del modelo (Movie) al ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // Obtiene la película en la posición actual.
        Movie movie = movies.get(position);

        // Establece el título de la película
        holder.titleTextView.setText(movie.getTitle());
        // Obtiene la URL de la imagen  de la película.
        String posterPath = movie.getPosterPath();

        if (posterPath != null && !posterPath.isEmpty()) {
            // Si el posterPath no es nulo ni vacío, forma la URL completa para cargar la imagen.
            String posterUrl = "https://image.tmdb.org/t/p/w500" + posterPath;
            Glide.with(holder.posterImageView.getContext())
                    .load(posterUrl)  // Carga la URL de la imagen.
                    .placeholder(R.drawable.default_image)  // Muestra una imagen por defecto mientras carga.
                    .into(holder.posterImageView);  // Coloca la imagen en el ImageView.
        } else {
            // Si no hay URL del poster, carga una imagen por defecto.
            Glide.with(holder.posterImageView.getContext())
                    .load(R.drawable.default_image)  // Imagen predeterminada si no hay URL de la película.
                    .into(holder.posterImageView);
        }
    }

    // Devuelve el número total de elementos en la lista (cuántas películas mostrar).
    @Override
    public int getItemCount() {
        return movies.size();  // Devuelve la cantidad de películas en la lista.
    }

    // Clase interna para representar un elemento del RecyclerView.
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;  // TextView para el título de la película.
        ImageView posterImageView;  // ImageView para el poster de la película.

        // Constructor que inicializa los componentes del ViewHolder.
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);  // Inicializa el TextView para el título.
            posterImageView = itemView.findViewById(R.id.posterImageView);  // Inicializa el ImageView para el poster.
        }
    }
}
