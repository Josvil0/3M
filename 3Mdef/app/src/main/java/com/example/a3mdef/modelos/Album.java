package com.example.a3mdef.modelos;

import java.util.List;

public class Album {
    private String name;
    private List<Image> images; // Lista de imágenes del álbum

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
