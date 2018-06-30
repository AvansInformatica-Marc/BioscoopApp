package nl.bioscoop.mijnbios;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.model.MovieShow;
import nl.bioscoop.biosapi.model.movie.Movie;
import nl.bioscoop.biosapi.utils.DataLoader;
import nl.bioscoop.mijnbios.adapters.MovieShowPickerAdapter;

public class ShowPickerActivity extends AppCompatActivity {
    private BiosAPI api;
    private Movie movie;
    private ListView items;
    private ArrayList<MovieShow> movieShows;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picker);loadActionbar();

        api = new BiosAPI(new DataLoader(this, Config.MAX_CACHE_SIZE_MB), getResources().getString(R.string.languageCode));

        movieShows = new ArrayList<>();

        items = findViewById(R.id.list);
        items.setAdapter(new MovieShowPickerAdapter(this, movieShows));
        items.setOnItemClickListener((adapterView, view, i, l) -> {
            MovieShow movieShow = ((MovieShowPickerAdapter) adapterView.getAdapter()).getItem(i);
            Intent intent = new Intent(this, TicketConfigActivity.class);
            intent.putExtra(Config.EXTRA_MOVIE, movie);
            intent.putExtra(Config.EXTRA_MOVIESHOW, movieShow);
            startActivity(intent);
        });

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(Config.EXTRA_MOVIE);
        if(movie != null) loadData(movie);
    }

    private void loadActionbar(){
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadData(@NonNull Movie movie){
        setTitle(getResources().getString(R.string.buyTickets) + " (" + movie.getTitle() + ")");

        api.getShowsForMovie(movie.getId(), (list) -> {
            movieShows.clear();
            movieShows.addAll(list);
            runOnUiThread(((MovieShowPickerAdapter) items.getAdapter())::notifyDataSetChanged);
        });
    }
}
