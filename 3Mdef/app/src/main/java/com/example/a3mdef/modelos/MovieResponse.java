package com.example.a3mdef.modelos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type Movie response.
 */
public class MovieResponse {
    @SerializedName("results")
    private List<Movie> results;

    /**
     * Gets results.
     *
     * @return the results
     */
    public List<Movie> getResults() {
        return results;
    }
}

