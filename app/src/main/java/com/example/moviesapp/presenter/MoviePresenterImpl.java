package com.example.moviesapp.presenter;

import com.example.moviesapp.model.Category;
import com.example.moviesapp.model.Movie;
import com.example.moviesapp.network.CategoryResponse;
import com.example.moviesapp.network.MovieApiService;
import com.example.moviesapp.network.MovieResponse;
import com.example.moviesapp.network.RetrofitClient;
import com.example.moviesapp.view.MovieView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MoviePresenterImpl implements MoviePresenter {
    private MovieView view;
    private static final String API_KEY = "358297e61b83f863bda0fbb210c642d0";

    public MoviePresenterImpl(MovieView view) {
        this.view = view;
    }

    @Override
    public void getMovies() {
        view.showLoading();
        MovieApiService apiService = RetrofitClient.getRetrofitInstance().create(MovieApiService.class);
        Call<MovieResponse> call = apiService.getMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showMovies(response.body().getMovies());
                } else {
                    view.showError("Error al obtener las peliculas");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }

    public void getCategories() {
        view.showLoading();
        MovieApiService apiService = RetrofitClient.getRetrofitInstance().create(MovieApiService.class);
        Call<CategoryResponse> call = apiService.getCategories(API_KEY);

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showCategories(response.body().getCategories());
                } else {
                    view.showError("Error al obtener las categorías");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }

    public void getMoviesByCategory(int categoryId) {
        view.showLoading();
        MovieApiService apiService = RetrofitClient.getRetrofitInstance().create(MovieApiService.class);
        Call<MovieResponse> call = apiService.getMoviesByCategory(categoryId, API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showMoviesByCategory(response.body().getMovies());
                } else {
                    view.showError("Error al obtener las películas de la categoría");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }

    public void searchMovies(String query) {
        view.showLoading();
        MovieApiService apiService = RetrofitClient.getRetrofitInstance().create(MovieApiService.class);
        Call<MovieResponse> call = apiService.searchMovies(query, API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showMovies(response.body().getMovies());
                } else {
                    view.showError("Error al buscar las películas");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }
}
