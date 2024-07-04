package com.example.moviesapp.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView overviewTextView;
    private TextView releaseDateTextView;
    private TextView voteAverageTextView;
    private ImageView posterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleTextView = findViewById(R.id.text_view_title);
        overviewTextView = findViewById(R.id.text_view_overview);
        releaseDateTextView = findViewById(R.id.text_view_release_date);
        voteAverageTextView = findViewById(R.id.text_view_vote_average);
        posterImageView = findViewById(R.id.image_view_poster);

        // Obtener los datos de la película desde el Intent
        Movie movie = getIntent().getParcelableExtra("movie");

        if (movie != null) {
            titleTextView.setText(movie.getTitle());
            overviewTextView.setText(movie.getOverview());
            releaseDateTextView.setText(movie.getReleaseDate());
            voteAverageTextView.setText(String.valueOf(movie.getVoteAverage()));

            String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
            Glide.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(posterImageView);
        }
    }
}