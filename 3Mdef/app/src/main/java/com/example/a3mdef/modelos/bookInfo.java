package com.example.a3mdef.modelos;

import java.io.Serializable;
import java.util.List;

/**
 * The type Book info.
 */
public class BookInfo implements Serializable { // Implementa Serializable
    private String title;
    private List<String> authors;
    private ImageLinks imageLinks;
    private String publishedDate; // Añadido para manejar la fecha de publicación.
    private String description;   // Añadido para manejar la descripción del libro.
    private List<String> categories; // Añadido para manejar las categorías.
    private String infoLink;  // Nueva propiedad para el enlace

    /**
     * Gets title.
     *
     * @return the title
     */
// Getters y setters
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets authors.
     *
     * @return the authors
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Sets authors.
     *
     * @param authors the authors
     */
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    /**
     * Gets image links.
     *
     * @return the image links
     */
    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    /**
     * Sets image links.
     *
     * @param imageLinks the image links
     */
    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    /**
     * Gets published date.
     *
     * @return the published date
     */
    public String getPublishedDate() {
        return publishedDate;
    }

    /**
     * Sets published date.
     *
     * @param publishedDate the published date
     */
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets categories.
     *
     * @return the categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Sets categories.
     *
     * @param categories the categories
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * The type Image links.
     */
// Clase interna para manejar los enlaces de imagen
    public static class ImageLinks implements Serializable { // También debe ser Serializable
        private String thumbnail;
        private String small;
        private String medium;
        private String large;
        private String extraLarge;

        /**
         * Gets thumbnail.
         *
         * @return the thumbnail
         */
// Getters y setters
        public String getThumbnail() {
            return thumbnail;
        }

        /**
         * Sets thumbnail.
         *
         * @param thumbnail the thumbnail
         */
        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        /**
         * Gets small.
         *
         * @return the small
         */
        public String getSmall() {
            return small;
        }

        /**
         * Sets small.
         *
         * @param small the small
         */
        public void setSmall(String small) {
            this.small = small;
        }

        /**
         * Gets medium.
         *
         * @return the medium
         */
        public String getMedium() {
            return medium;
        }

        /**
         * Sets medium.
         *
         * @param medium the medium
         */
        public void setMedium(String medium) {
            this.medium = medium;
        }

        /**
         * Gets large.
         *
         * @return the large
         */
        public String getLarge() {
            return large;
        }

        /**
         * Sets large.
         *
         * @param large the large
         */
        public void setLarge(String large) {
            this.large = large;
        }

        /**
         * Gets extra large.
         *
         * @return the extra large
         */
        public String getExtraLarge() {
            return extraLarge;
        }

        /**
         * Sets extra large.
         *
         * @param extraLarge the extra large
         */
        public void setExtraLarge(String extraLarge) {
            this.extraLarge = extraLarge;
        }

        @Override
        public String toString() {
            return "Thumbnail: " + thumbnail + ", Small: " + small + ", Medium: " + medium +
                    ", Large: " + large + ", ExtraLarge: " + extraLarge;
        }


    }

    /**
     * Gets info link.
     *
     * @return the info link
     */
    public String getInfoLink() {
        return infoLink;
    }

    /**
     * Sets info link.
     *
     * @param infoLink the info link
     */
    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }
}
