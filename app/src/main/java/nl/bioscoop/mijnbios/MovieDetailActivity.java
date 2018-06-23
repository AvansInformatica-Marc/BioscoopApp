package nl.bioscoop.mijnbios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nl.bioscoop.mijnbios.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        View view = toolbar.getChildAt(0);
        if(view instanceof AppCompatTextView) view.setTransitionName("movieTitleTransition");

        loadActionbar();

        Intent intent = getIntent();
        @Nullable Movie movie = (Movie) intent.getSerializableExtra("Movie");
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

    public void loadMovieData(@NonNull Movie movie){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setTitle(movie.getTitle());

        Picasso.with(this).load(movie.getPoster()).into((ImageView) findViewById(R.id.actionBarImage));

        TextView description = findViewById(R.id.description);
        description.setText(movie.getDescription());
    }
}
