package nl.bioscoop.mijnbios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nl.bioscoop.mijnbios.model.Movie;
import nl.bioscoop.mijnbios.utils.DataLoader;

public class MainActivity extends AppCompatActivity {
    private Api api;
    private GridView movieGrid;
    private ArrayList<Movie> movies;
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
            Movie movie = ((MovieAdapter) movieGrid.getAdapter()).getItem(i);
            if(movie != null) showMovieDetail(movie, view.findViewById(R.id.poster), view.findViewById(R.id.title));
        });

        api = new Api(new DataLoader(this));

        showAllMovies();
    }

    private void showMovieDetail(@NonNull Movie movie, @NonNull ImageView poster, @NonNull TextView title){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(poster, "moviePosterTransition"), Pair.create(title, "movieTitleTransition"));
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent, options.toBundle());
    }

    private void showAllMovies(){
        swipeRefreshLayout.setRefreshing(true);
        api.getAllMovies((moviesList) -> {
            movies.clear();
            movies.addAll(moviesList);
            runOnUiThread(((MovieAdapter) movieGrid.getAdapter())::notifyDataSetChanged);
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
