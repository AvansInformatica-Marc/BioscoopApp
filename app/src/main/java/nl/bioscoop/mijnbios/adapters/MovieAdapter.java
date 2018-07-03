package nl.bioscoop.mijnbios.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.bioscoop.biosapi.model.Movie;
import nl.bioscoop.mijnbios.R;
import nl.bioscoop.mijnbios.utils.Images;

import static nl.bioscoop.mijnbios.utils.Views.inflateLayout;

public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(@NonNull Context context, @NonNull ArrayList<Movie> list){
        super(context, 0, list);
    }

    @Override @NonNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @NonNull View view = convertView != null ? convertView : inflateLayout(R.layout.activity_main_movie_item, parent);
        @Nullable Movie movie = getItem(position);

        if(movie == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            TextView movieTitle = view.findViewById(R.id.title);
            movieTitle.setText(movie.getTitle());

            if (movie.getPosterImage() != null)
                Images.loadImage(movie.getPosterImage(), view.findViewById(R.id.poster));
        }

        return view;
    }
}
