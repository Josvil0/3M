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

/**
 * Adaptador para RecyclerView que maneja tres tipos de elementos: artistas, álbumes y canciones.
 * Utiliza múltiples tipos de vistas para mostrar los diferentes elementos en el RecyclerView.
 */
public class SpotifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Artist> artists;  // Lista de artistas
    private List<Album> albums;    // Lista de álbumes
    private List<Track> tracks;    // Lista de canciones

    // Constantes para identificar cada tipo de vista
    private static final int TYPE_ARTIST = 0;
    private static final int TYPE_ALBUM = 1;
    private static final int TYPE_TRACK = 2;

    /**
     * Constructor del adaptador. Inicializa las listas de artistas, álbumes y canciones.
     */
    public SpotifyAdapter() {
        this.artists = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.tracks = new ArrayList<>();
    }

    /**
     * Establece la lista de artistas en el adaptador.
     *
     * @param artists Lista de artistas a mostrar.
     */
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
    }

    /**
     * Establece la lista de álbumes en el adaptador.
     *
     * @param albums Lista de álbumes a mostrar.
     */
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
    }

    /**
     * Establece la lista de canciones en el adaptador.
     *
     * @param tracks Lista de canciones a mostrar.
     */
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
    }

    /**
     * Devuelve el tipo de vista para el elemento en la posición especificada.
     *
     * @param position La posición del elemento en el RecyclerView.
     * @return Un entero que indica el tipo de vista (ARTIST, ALBUM, TRACK).
     */
    @Override
    public int getItemViewType(int position) {
        if (position < artists.size()) {
            return TYPE_ARTIST;  // Es un artista
        } else if (position < artists.size() + albums.size()) {
            return TYPE_ALBUM;   // Es un álbum
        } else {
            return TYPE_TRACK;   // Es una canción
        }
    }

    /**
     * Crea la vista correspondiente para el tipo de elemento especificado.
     *
     * @param parent El contenedor donde se agregará la vista.
     * @param viewType El tipo de vista (ARTIST, ALBUM, TRACK).
     * @return Un ViewHolder correspondiente al tipo de vista.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ARTIST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
                return new ArtistViewHolder(view);
            case TYPE_ALBUM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
                return new AlbumViewHolder(view);
            case TYPE_TRACK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
                return new CancionViewHolder(view);
            default:
                throw new IllegalArgumentException("Unknown view type");  // Tipo de vista desconocido
        }
    }

    /**
     * Vincula los datos del modelo con las vistas del ViewHolder.
     *
     * @param holder El ViewHolder que contiene las vistas.
     * @param position La posición del elemento en el RecyclerView.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);  // Obtiene el tipo de vista para la posición

        // Si el tipo de vista es un artista
        if (viewType == TYPE_ARTIST) {
            if (artists != null && !artists.isEmpty()) {
                ArtistViewHolder artistViewHolder = (ArtistViewHolder) holder;
                Artist artist = artists.get(position);
                artistViewHolder.nameTextView.setText(artist.getName());
                if (artist.getImages() != null && !artist.getImages().isEmpty()) {
                    Glide.with(artistViewHolder.itemView.getContext())
                            .load(artist.getImages().get(0).getUrl())
                            .into(artistViewHolder.imageView);
                }
            }
        }
        // Si el tipo de vista es un álbum
        else if (viewType == TYPE_ALBUM) {
            if (albums != null && !albums.isEmpty()) {
                AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;
                Album album = albums.get(position - artists.size());
                albumViewHolder.nameTextView.setText(album.getName());
                if (album.getImages() != null && !album.getImages().isEmpty()) {
                    Glide.with(albumViewHolder.itemView.getContext())
                            .load(album.getImages().get(0).getUrl())
                            .into(albumViewHolder.imageView);
                }
            }
        }
        // Si el tipo de vista es una canción
        else if (viewType == TYPE_TRACK) {
            if (tracks != null && !tracks.isEmpty()) {
                CancionViewHolder cancionViewHolder = (CancionViewHolder) holder;
                Track track = tracks.get(position - artists.size() - albums.size());
                cancionViewHolder.nameTextView.setText(track.getName());
                if (track.getAlbum() != null && track.getAlbum().getImages() != null && !track.getAlbum().getImages().isEmpty()) {
                    Glide.with(cancionViewHolder.itemView.getContext())
                            .load(track.getAlbum().getImages().get(0).getUrl())
                            .into(cancionViewHolder.imageView);
                }
            }
        }
    }

    /**
     * Devuelve el número total de elementos (artistas, álbumes y canciones) en el RecyclerView.
     *
     * @return El número total de elementos.
     */
    @Override
    public int getItemCount() {
        return artists.size() + albums.size() + tracks.size();
    }

    /**
     * The type Artist view holder.
     */
// ViewHolder para artistas
    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Name text view.
         */
        public TextView nameTextView;
        /**
         * The Image view.
         */
        public ImageView imageView;

        /**
         * Instantiates a new Artist view holder.
         *
         * @param view the view
         */
        public ArtistViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.artist_name);
            imageView = view.findViewById(R.id.artist_image);
        }
    }

    /**
     * The type Album view holder.
     */
// ViewHolder para álbumes
    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Name text view.
         */
        public TextView nameTextView;
        /**
         * The Image view.
         */
        public ImageView imageView;

        /**
         * Instantiates a new Album view holder.
         *
         * @param view the view
         */
        public AlbumViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.album_name);
            imageView = view.findViewById(R.id.album_image);
        }
    }

    /**
     * The type Cancion view holder.
     */
// ViewHolder para canciones
    public static class CancionViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Name text view.
         */
        public TextView nameTextView;
        /**
         * The Image view.
         */
        public ImageView imageView;

        /**
         * Instantiates a new Cancion view holder.
         *
         * @param view the view
         */
        public CancionViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.track_name);
            imageView = view.findViewById(R.id.track_image);
        }
    }
}


