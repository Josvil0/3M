package com.example.a3mdef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.a3mdef.modelos.Album;
import com.example.a3mdef.modelos.Artist;
import com.example.a3mdef.modelos.Track;

import java.util.ArrayList;
import java.util.List;

// Adaptador para RecyclerView, que maneja tres tipos de elementos: artistas, álbumes y canciones (tracks)
public class SpotifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Artist> artists;  // Lista de artistas
    private List<Album> albums;    // Lista de álbumes
    private List<Track> tracks;    // Lista de canciones

    // Constantes para identificar cada tipo de elemento en el RecyclerView
    private static final int TYPE_ARTIST = 0;
    private static final int TYPE_ALBUM = 1;
    private static final int TYPE_TRACK = 2;

    // Constructor del adaptador
    public SpotifyAdapter() {
        this.artists = new ArrayList<>();  // Inicializa la lista de artistas
        this.albums = new ArrayList<>();   // Inicializa la lista de álbumes
        this.tracks = new ArrayList<>();   // Inicializa la lista de canciones
    }

    // Métodos setter para actualizar las listas de artistas, álbumes y canciones
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
    }

    // Determina el tipo de vista para el elemento en la posición especificada
    @Override
    public int getItemViewType(int position) {
        if (position < artists.size()) {
            return TYPE_ARTIST;
        } else if (position < artists.size() + albums.size()) {
            return TYPE_ALBUM;
        } else {
            return TYPE_TRACK;
        }
    }

    // Crea la vista correspondiente para el tipo de elemento
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ARTIST:
                // Infla el layout para los artistas
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
                return new ArtistViewHolder(view);
            case TYPE_ALBUM:
                // Infla el layout para los álbumes
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
                return new AlbumViewHolder(view);
            case TYPE_TRACK:
                // Infla el layout para las canciones
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
                return new CancionViewHolder(view);
            default:
                throw new IllegalArgumentException("Unknown view type");  // Si el tipo de vista no es conocido, lanza una excepción
        }
    }

    // Vincula los datos del modelo con las vistas
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);  // Obtiene el tipo de vista para la posición actual

        // Si el tipo de vista es un artista
        if (viewType == TYPE_ARTIST) {
            if (artists != null && !artists.isEmpty()) {  // Verifica que la lista de artistas no esté vacía
                ArtistViewHolder artistViewHolder = (ArtistViewHolder) holder;  // Convierte el ViewHolder a ArtistViewHolder
                Artist artist = artists.get(position);  // Obtiene el artista en la posición actual
                artistViewHolder.nameTextView.setText(artist.getName());  // Establece el nombre del artista
                // Si el artista tiene imágenes, carga la primera imagen con Glide
                if (artist.getImages() != null && !artist.getImages().isEmpty()) {
                    Glide.with(artistViewHolder.itemView.getContext())
                            .load(artist.getImages().get(0).getUrl())  // Carga la imagen del artista
                            .into(artistViewHolder.imageView);  // Asigna la imagen a la vista correspondiente
                }
            }
        }
        // Si el tipo de vista es un álbum
        else if (viewType == TYPE_ALBUM) {
            if (albums != null && !albums.isEmpty()) {  // Verifica que la lista de álbumes no esté vacía
                AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;  // Convierte el ViewHolder a AlbumViewHolder
                Album album = albums.get(position - artists.size());  // Ajusta la posición para los álbumes
                albumViewHolder.nameTextView.setText(album.getName());  // Establece el nombre del álbum
                // Si el álbum tiene imágenes, carga la primera imagen con Glide
                if (album.getImages() != null && !album.getImages().isEmpty()) {
                    Glide.with(albumViewHolder.itemView.getContext())
                            .load(album.getImages().get(0).getUrl())  // Carga la imagen del álbum
                            .into(albumViewHolder.imageView);  // Asigna la imagen a la vista correspondiente
                }
            }
        }
        // Si el tipo de vista es una canción
        else if (viewType == TYPE_TRACK) {
            if (tracks != null && !tracks.isEmpty()) {  // Verifica que la lista de canciones no esté vacía
                CancionViewHolder cancionViewHolder = (CancionViewHolder) holder;  // Convierte el ViewHolder a TrackViewHolder
                Track track = tracks.get(position - artists.size() - albums.size());  // Ajusta la posición para las canciones
                cancionViewHolder.nameTextView.setText(track.getName());  // Establece el nombre de la canción
                // Si la canción tiene un álbum y el álbum tiene imágenes, carga la primera imagen del álbum con Glide
                if (track.getAlbum() != null && track.getAlbum().getImages() != null && !track.getAlbum().getImages().isEmpty()) {
                    Glide.with(cancionViewHolder.itemView.getContext())
                            .load(track.getAlbum().getImages().get(0).getUrl())  // Carga la imagen del álbum de la canción
                            .into(cancionViewHolder.imageView);  // Asigna la imagen a la vista correspondiente
                }
            }
        }
    }

    // Retorna el número total de elementos en las tres listas
    @Override
    public int getItemCount() {
        return artists.size() + albums.size() + tracks.size();  // Devuelve la cantidad total de elementos (artistas + álbumes + canciones)
    }

    // ViewHolder para artistas
    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;  // Vista para el nombre del artista
        public ImageView imageView;    // Vista para la imagen del artista
        public CardView cardView;      // Vista para el CardView (envoltorio)

        public ArtistViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.artist_name);  // Encuentra la vista del nombre
            imageView = view.findViewById(R.id.artist_image);    // Encuentra la vista de la imagen
        }
    }

    // ViewHolder para álbumes
    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;  // Vista para el nombre del álbum
        public ImageView imageView;    // Vista para la imagen del álbum
        public CardView cardView;      // Vista para el CardView (envoltorio)

        public AlbumViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.album_name);  // Encuentra la vista del nombre
            imageView = view.findViewById(R.id.album_image);    // Encuentra la vista de la imagen
        }
    }

    // ViewHolder para canciones
    public static class CancionViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;  // Vista para el nombre de la canción
        public ImageView imageView;    // Vista para la imagen del álbum de la canción
        public CardView cardView;      // Vista para el CardView (envoltorio)

        public CancionViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.track_name);  // Encuentra la vista del nombre
            imageView = view.findViewById(R.id.track_image);    // Encuentra la vista de la imagen
        }
    }
}

