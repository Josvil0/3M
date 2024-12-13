package com.example.a3mdef.modelos;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * The type Movie.
 */
public class Movie implements Serializable {  // Implementa Serializable

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private double voteAverage;

    /**
     * Gets title.
     *
     * @return the title
     */
// Getters
    public String getTitle() {
        return title;
    }

    /**
     * Gets poster path.
     *
     * @return the poster path
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Gets release date.
     *
     * @return the release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets overview.
     *
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Gets vote average.
     *
     * @return the vote average
     */
    public double getVoteAverage() {
        return voteAverage;
    }
}
