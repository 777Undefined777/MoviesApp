package com.example.moviesapp.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.example.moviesapp.R;
import com.example.moviesapp.model.Category;
import com.example.moviesapp.model.Movie;
import com.example.moviesapp.presenter.MoviePresenter;
import com.example.moviesapp.presenter.MoviePresenterImpl;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements MovieView {
    private RecyclerView recyclerViewMovies;
    private RecyclerView recyclerViewCategories;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private MovieAdapter movieAdapter;
    private CategoriesAdapter categoriesAdapter;
    private MoviePresenter presenter;
    private SearchView searchView;
    private ViewPager2 viewPager;
    private CarouselAdapter carouselAdapter;
    private Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMovies = findViewById(R.id.recycler_view);
        recyclerViewCategories = findViewById(R.id.recycler_view_categories);
        progressBar = findViewById(R.id.progress_bar);
        errorTextView = findViewById(R.id.error_text_view);
        searchView = findViewById(R.id.search_view);
        viewPager = findViewById(R.id.view_pager);

        // Configure the carousel adapter and the view pager
        carouselAdapter = new CarouselAdapter();
        viewPager.setAdapter(carouselAdapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL); // Set the orientation to vertical
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // Slide every 3 seconds
            }
        });

        movieAdapter = new MovieAdapter();
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(movieAdapter);

        categoriesAdapter = new CategoriesAdapter();
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategories.setAdapter(categoriesAdapter);

        int verticalSpaceHeight = 1; // Space between items
        recyclerViewCategories.addItemDecoration(new CategoryItemDecoration(verticalSpaceHeight));

        categoriesAdapter.setOnCategoryClickListener(new CategoriesAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                presenter.getMoviesByCategory(category.getId());
            }
        });

        // Configure Glide with custom OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(getSSLSocketFactory(), getTrustManager())
                .hostnameVerifier((hostname, session) -> true)
                .build();

        Glide.get(this).getRegistry().replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory((Call.Factory) client));

        presenter = new MoviePresenterImpl(this);
        presenter.getMovies();
        presenter.getCategories();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchMovies(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void showMovies(List<Movie> movies) {
        movieAdapter.setMovies(movies);
        carouselAdapter.setMovies(movies);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter.setCategories(categories);
    }

    @Override
    public void showMoviesByCategory(List<Movie> movies) {
        movieAdapter.setMovies(movies);
    }

    @Override
    public void showError(String message) {
        errorTextView.setText(message);
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{getTrustManager()}, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private X509TrustManager getTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {}

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }
}
