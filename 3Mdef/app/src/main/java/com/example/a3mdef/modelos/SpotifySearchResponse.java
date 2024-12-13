package com.example.a3mdef.modelos;

import java.util.List;

/**
 * The type Spotify search response.
 */
public class SpotifySearchResponse {
    private Artists artists;
    private Albums albums;
    private Tracks tracks;

    /**
     * Gets artists.
     *
     * @return the artists
     */
    public Artists getArtists() {
        return artists;
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public Albums getAlbums() {
        return albums;
    }

    /**
     * Gets tracks.
     *
     * @return the tracks
     */
    public Tracks getTracks() {
        return tracks;
    }
}

