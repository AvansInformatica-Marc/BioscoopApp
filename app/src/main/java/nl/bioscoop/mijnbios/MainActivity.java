package nl.bioscoop.mijnbios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.bioscoop.mijnbios.model.Movie;
import nl.bioscoop.mijnbios.utils.DataLoader;

public class MainActivity extends Activity {
    private DataLoader dataLoader;
    private GridView movieGrid;
    private ArrayList<Movie> movies;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::showAllMovies);

        movies = new ArrayList<>();
        movieGrid = findViewById(R.id.grid);
        movieGrid.setAdapter(new MovieAdapter(MainActivity.this, movies));
        movieGrid.setOnItemClickListener((adapterView, view, i, l) -> {
            Movie movie = ((MovieAdapter) movieGrid.getAdapter()).getItem(i);
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra("Movie", movie);
            startActivity(intent);
        });

        dataLoader = new DataLoader(this);

        showAllMovies();
    }

    private void showAllMovies(){
        dataLoader.load("https://mijnbios.herokuapp.com/api/v1/movies", (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONArray moviesList = new JSONArray(responseBody);
                movies.clear();
                for (int i = 0; i < moviesList.length(); i++){
                    @Nullable JSONObject movie = moviesList.optJSONObject(i);
                    if(movie != null) movies.add(new Movie(movie));
                }
                runOnUiThread(() -> ((ArrayAdapter) movieGrid.getAdapter()).notifyDataSetChanged());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
