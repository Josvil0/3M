package com.example.a3mdef.modelos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TMDbResponse {
    @SerializedName("results")
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }
}
