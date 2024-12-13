package com.example.a3mdef.modelos;

import java.io.Serializable;

/**
 * The type Book.
 */
public class Book implements Serializable {
    private String id; // Añadir campo 'id'
    private BookInfo volumeInfo;

    /**
     * Gets id.
     *
     * @return the id
     */
// Getters y setters
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets volume info.
     *
     * @return the volume info
     */
    public BookInfo getVolumeInfo() {
        return volumeInfo;
    }

    /**
     * Sets volume info.
     *
     * @param volumeInfo the volume info
     */
    public void setVolumeInfo(BookInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + volumeInfo.getTitle() + ", Authors: " + volumeInfo.getAuthors() +
                ", ImageLinks: " + volumeInfo.getImageLinks();
    }
}

