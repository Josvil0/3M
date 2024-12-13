package com.example.a3mdef.modelos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * The type Tm db response.
 */
public class TMDbResponse {
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
