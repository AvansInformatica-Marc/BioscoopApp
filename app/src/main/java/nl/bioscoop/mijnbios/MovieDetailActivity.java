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

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.model.Movie;
import nl.bioscoop.biosapi.utils.DataLoader;
import nl.bioscoop.mijnbios.utils.Images;
import nl.bioscoop.mijnbios.utils.Views;

public class MovieDetailActivity extends AppCompatActivity {
    private BiosAPI api;
    private LinearLayout detailsList;
    private Movie movie;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        loadActionbar();

        api = new BiosAPI(new DataLoader(this, Config.MAX_CACHE_SIZE_MB), getResources().getString(R.string.languageCode));

        detailsList = findViewById(R.id.detailsList);

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(Config.EXTRA_MOVIE);
        if(movie != null) loadMovieData(movie, true);
    }

    private void loadActionbar(){
        setSupportActionBar(findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadMovieData(@NonNull Movie movie, boolean tryReloadDataWhenNull){
        this.movie = movie;

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setTitle(movie.getTitle());

        if (movie.getHeaderImage() != null)
            Images.loadImage(movie.getHeaderImage(), findViewById(R.id.actionBarImage));

        if (movie.getDescription() != null)
            detailsList.addView(generateDetailView(getResources().getString(R.string.description), movie.getDescription(), R.drawable.ic_info));

        if (movie.getDuration() != null)
            detailsList.addView(generateDetailView(getResources().getString(R.string.duration), movie.getDuration() + " min.", R.drawable.ic_time));

        /*if(tryReloadDataWhenNull && (movie.getHeaderImage() == null) || movie.getDescription() == null) api.getMovieDetail(movie.getID(), (newMovie) -> runOnUiThread(() -> {
            loadMovieData(newMovie, false);
        }));*/
    }

    private RelativeLayout generateDetailView(@NonNull String title, @NonNull String content, @Nullable Integer imageResource){
        RelativeLayout descriptionView = Views.inflateLayout(R.layout.activity_movie_detail_item, detailsList);

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
        Intent intent = new Intent(this, ShowPickerActivity.class);
        intent.putExtra(Config.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
