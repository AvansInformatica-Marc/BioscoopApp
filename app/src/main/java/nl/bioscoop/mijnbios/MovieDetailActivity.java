package nl.bioscoop.mijnbios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nl.bioscoop.mijnbios.model.movie.MoviePoster;
import nl.bioscoop.mijnbios.utils.DataLoader;

public class MovieDetailActivity extends AppCompatActivity {
    private Api api;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        loadActionbar();

        api = new Api(new DataLoader(this));

        Intent intent = getIntent();
        @Nullable MoviePoster movie = (MoviePoster) intent.getSerializableExtra("MoviePoster");
        if(movie != null) loadMovieData(movie);
    }

    private void loadActionbar(){
        setSupportActionBar(findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void loadMovieData(@NonNull MoviePoster moviePoster){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setTitle(moviePoster.getTitle());

        api.getMovieDetail(moviePoster.getId(), (movie) -> runOnUiThread(() -> {
            if(actionBar != null) actionBar.setTitle(movie.getTitle());

            Picasso.with(this).load(movie.getBackdrop()).into((ImageView) findViewById(R.id.actionBarImage));

            TextView description = findViewById(R.id.description);
            description.setText(movie.getDescription());
        }));
    }
}
