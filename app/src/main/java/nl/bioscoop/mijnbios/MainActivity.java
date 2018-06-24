package nl.bioscoop.mijnbios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

import nl.bioscoop.mijnbios.model.movie.MoviePoster;
import nl.bioscoop.mijnbios.utils.DataLoader;

public class MainActivity extends AppCompatActivity {
    private Api api;
    private GridView movieGrid;
    private ArrayList<MoviePoster> movies;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::showAllMovies);

        movies = new ArrayList<>();

        movieGrid = findViewById(R.id.grid);
        movieGrid.setAdapter(new MovieAdapter(MainActivity.this, movies));
        movieGrid.setOnItemClickListener((adapterView, view, i, l) -> {
            MoviePoster movie = ((MovieAdapter) movieGrid.getAdapter()).getItem(i);
            if(movie != null) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("MoviePoster", movie);
                startActivity(intent);
            }
        });

        api = new Api(new DataLoader(this));

        showAllMovies();
    }

    private void showAllMovies(){
        swipeRefreshLayout.setRefreshing(true);
        api.getAllMoviePosters((moviesList) -> {
            movies.clear();
            movies.addAll(moviesList);
            runOnUiThread(((MovieAdapter) movieGrid.getAdapter())::notifyDataSetChanged);
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
