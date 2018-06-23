package nl.bioscoop.mijnbios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nl.bioscoop.mijnbios.model.Movie;

import static nl.bioscoop.mijnbios.utils.Views.inflateLayout;

public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(@NonNull Context context, @NonNull ArrayList<Movie> list){
        super(context, 0, list);
    }

    @Override @NonNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @NonNull View view = convertView != null ? convertView : inflateLayout(R.layout.movie_griditem, parent);
        @Nullable Movie movie = getItem(position);

        if(movie == null) {
            view.setVisibility(View.GONE);
        } else {
            TextView movieTitle = view.findViewById(R.id.title);
            movieTitle.setText(movie.getTitle());
            ImageView moviePoster = view.findViewById(R.id.poster);
            Picasso.with(getContext()).load(movie.getPoster()).into(moviePoster);
        }

        return view;
    }
}
