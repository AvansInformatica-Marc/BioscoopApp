package nl.bioscoop.mijnbios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import nl.bioscoop.biosapi.model.MovieShow;

import static nl.bioscoop.mijnbios.utils.Views.inflateLayout;

public class MovieShowPickerAdapter extends ArrayAdapter<MovieShow> {
    public MovieShowPickerAdapter(@NonNull Context context, @NonNull ArrayList<MovieShow> list){
        super(context, 0, list);
    }

    @Override @NonNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @NonNull View view = convertView != null ? convertView : inflateLayout(R.layout.movie_show_picker_item, parent);
        @Nullable MovieShow movieShow = getItem(position);

        if(movieShow == null) {
            view.setVisibility(View.GONE);
        } else {
            View card = view.findViewById(R.id.card);
            card.setOnClickListener(view1 -> ((ListView) parent).performItemClick(convertView, position, position));

            TextView datetime = view.findViewById(R.id.datetime);
            datetime.setText(DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(movieShow.getDatetime()) + " - " +
                    DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(movieShow.getDatetime()));

            TextView location = view.findViewById(R.id.location);
            location.setText(movieShow.getLocation());
        }

        return view;
    }
}
