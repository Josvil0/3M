package com.example.a3mdef.modelos;

import java.util.List;

public class bookInfo {
    private String title;
    private List<String> authors;
    private ImageLinks imageLinks; // Agrega el campo para las imágenes

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    // Clase interna para manejar los enlaces de imagen
    public class ImageLinks {
        private String thumbnail;
        private String small;
        private String medium;
        private String large;
        private String extraLarge;

        // Getters y setters

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getExtraLarge() {
            return extraLarge;
        }

        public void setExtraLarge(String extraLarge) {
            this.extraLarge = extraLarge;
        }
    }

}
