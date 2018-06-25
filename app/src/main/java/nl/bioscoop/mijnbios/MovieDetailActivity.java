package nl.bioscoop.mijnbios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.model.movie.MovieDetails;
import nl.bioscoop.biosapi.model.movie.MoviePoster;
import nl.bioscoop.biosapi.utils.DataLoader;
import nl.bioscoop.mijnbios.utils.Views;

public class MovieDetailActivity extends AppCompatActivity {
    private BiosAPI api;
    private LinearLayout detailsList;
    private MovieDetails movie;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        loadActionbar();

        api = new BiosAPI(new DataLoader(this, Config.MAX_CACHE_SIZE_MB), getResources().getString(R.string.languageCode));

        detailsList = findViewById(R.id.detailsList);

        Intent intent = getIntent();
        @Nullable MoviePoster movie = (MoviePoster) intent.getSerializableExtra(Config.EXTRA_MOVIEPOSTER);
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

    private void loadMovieData(@NonNull MoviePoster moviePoster){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setTitle(moviePoster.getTitle());

        api.getMovieDetail(moviePoster.getId(), (movie) -> runOnUiThread(() -> {
            this.movie = movie;
            if(actionBar != null) actionBar.setTitle(movie.getTitle());

            Picasso.with(this).load(movie.getBackdrop()).into((ImageView) findViewById(R.id.actionBarImage));

            detailsList.addView(generateDetailView(getResources().getString(R.string.description), movie.getDescription(), R.drawable.ic_info));
        }));
    }

    private RelativeLayout generateDetailView(@NonNull String title, @NonNull String content, @Nullable Integer imageResource){
        RelativeLayout descriptionView = Views.inflateLayout(R.layout.movie_content_item, detailsList);

        ImageView icon = descriptionView.findViewById(R.id.icon);
        if(imageResource != null) icon.setImageResource(imageResource);
        else icon.setVisibility(View.INVISIBLE);

        TextView titleView = descriptionView.findViewById(R.id.title);
        titleView.setText(title);

        TextView contentView = descriptionView.findViewById(R.id.content);
        contentView.setText(content);

        return descriptionView;
    }

    public void book(View view){
        Intent intent = new Intent(this, MovieShowPicker.class);
        intent.putExtra(Config.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
