package com.example.moviesapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<CategoryResponse> getCategories(@Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<MovieResponse> getMoviesByCategory(@Query("with_genres") int categoryId, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> searchMovies(@Query("query") String query, @Query("api_key") String apiKey);

}
