package com.example.a3mdef;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.a3mdef.modelos.Movie;

/**
 * Fragmento que muestra los detalles de una película, incluyendo el título, sinopsis, fecha de lanzamiento,
 * calificación y la imagen del póster.
 * Este fragmento recibe un objeto Movie como argumento para mostrar la información.
 */
public class MovieDetailFragment extends Fragment {

    private static final String ARG_MOVIE = "movie";  // Clave para pasar el objeto Movie entre fragmentos

    private ImageView moviePosterDetail;  // Vista para mostrar el póster de la película
    private TextView movieTitleDetail;  // Vista para mostrar el título de la película
    private TextView movieOverviewDetail;  // Vista para mostrar la sinopsis de la película
    private TextView movieReleaseDateDetail;  // Vista para mostrar la fecha de lanzamiento
    private TextView movieRatingText;  // Vista para mostrar la calificación de la película
    private Movie movie;  // Objeto Movie que contiene los datos de la película

    /**
     * Crea una nueva instancia del fragmento con la película pasada como argumento.
     *
     * @param movie La película cuyo detalle se mostrará en el fragmento.
     * @return Una nueva instancia de MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);  // Pasa la película como argumento serializado
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Se llama al crear el fragmento. Aquí se recupera el objeto Movie desde los argumentos pasados.
     *
     * @param savedInstanceState El estado guardado del fragmento (si existe).
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recupera el objeto Movie desde los argumentos del fragmento
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(ARG_MOVIE);
        }
    }

    /**
     * Infla la vista del fragmento y vincula los datos de la película con las vistas correspondientes.
     *
     * @param inflater El inflador de la vista.
     * @param container El contenedor donde se añadirá la vista.
     * @param savedInstanceState El estado guardado del fragmento (si existe).
     * @return La vista inflada del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Referencias a las vistas
        moviePosterDetail = view.findViewById(R.id.moviePosterDetail);
        movieTitleDetail = view.findViewById(R.id.movieTitleDetail);
        movieOverviewDetail = view.findViewById(R.id.movieOverviewDetail);
        movieReleaseDateDetail = view.findViewById(R.id.movieReleaseDateDetail);
        movieRatingText = view.findViewById(R.id.movieRatingText);

        // Obtener la película pasada desde los argumentos del fragmento anterior
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");
            if (movie != null) {
                // Mostrar los datos de la película en las vistas correspondientes
                movieTitleDetail.setText(movie.getTitle());
                movieOverviewDetail.setText(movie.getOverview());
                movieReleaseDateDetail.setText("Release Date: " + movie.getReleaseDate());
                movieRatingText.setText("Rating: " + movie.getVoteAverage() + " / 10");

                // Cargar la imagen del póster usando Glide
                String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                Glide.with(this)
                        .load(imageUrl)
                        .into(moviePosterDetail);  // Carga el póster en la vista correspondiente
            }
        }

        return view;
    }
}
