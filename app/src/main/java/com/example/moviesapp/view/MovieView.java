package com.example.moviesapp.view;

import com.example.moviesapp.model.Category;
import com.example.moviesapp.model.Movie;

import java.util.List;

public interface MovieView {
    void showMovies(List<Movie> movies);
    void showCategories(List<Category> categories);
    void showMoviesByCategory(List<Movie> movies);
    void showError(String message);
    void showLoading();

    void hideLoading();
}
