package com.example.a3mdef.modelos;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("volumeInfo")
    private bookInfo volumeInfo;

    public bookInfo getVolumeInfo() {
        return volumeInfo;
    }
}

