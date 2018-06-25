package nl.bioscoop.mijnbios;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.model.movie.Movie;
import nl.bioscoop.biosapi.model.movie.MovieDetails;
import nl.bioscoop.biosapi.model.movie.MoviePoster;
import nl.bioscoop.biosapi.utils.DataLoader;

public class MovieShowPicker extends Activity {
    private BiosAPI api;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_show_picker);loadActionbar();

        api = new BiosAPI(new DataLoader(this, Config.MAX_CACHE_SIZE_MB), getResources().getString(R.string.languageCode));

        Intent intent = getIntent();
        @Nullable Movie movie = (Movie) intent.getSerializableExtra(Config.EXTRA_MOVIE);
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
        if(movie instanceof MoviePoster) setTitle(getResources().getString(R.string.book) + " (" + ((MoviePoster) movie).getTitle() + ")");
        else if(movie instanceof MovieDetails) setTitle(getResources().getString(R.string.book) + " (" + ((MovieDetails) movie).getTitle() + ")");

        api.getShowsForMovie(movie.getId(), (list) -> {
            // TODO
        });
    }
}
