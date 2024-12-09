package com.example.a3mdef.modelos;

import java.util.List;

public class Artist {
    private String name;
    private List<Image> images; // Lista de imágenes del artista

    public String getName() {
        return name;
    }

    public List<Image> getImages() {
        return images;
    }

    public static class Image {
        private String url;

        public String getUrl() {
            return url;
        }
    }
}
