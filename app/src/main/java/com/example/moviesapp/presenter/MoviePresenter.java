package com.example.moviesapp.presenter;



public interface MoviePresenter {
    void getMovies();
    void getCategories();
    void getMoviesByCategory(int id);
    void searchMovies(String query);

}

