package com.example.a3mdef.modelos;

import java.util.List;

/**
 * The type Album.
 */
public class Album {
    private String name;
    private List<Image> images; // Lista de imágenes del álbum

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets images.
     *
     * @return the images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * The type Image.
     */
    public static class Image {
        private String url;

        /**
         * Gets url.
         *
         * @return the url
         */
        public String getUrl() {
            return url;
        }
    }
}
