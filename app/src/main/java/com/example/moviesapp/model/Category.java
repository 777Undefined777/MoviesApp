package com.example.moviesapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Category {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("movies")
    private List<Movie> movies;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
