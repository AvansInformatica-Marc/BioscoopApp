package nl.bioscoop.mijnbios.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.bioscoop.biosapi.model.Cinema;
import nl.bioscoop.mijnbios.R;
import nl.bioscoop.mijnbios.utils.Views;

public class CinemaAdapter extends ArrayAdapter<Cinema> {
    public CinemaAdapter(@NonNull Context context, @NonNull ArrayList<Cinema> list){
        super(context, 0, list);
    }

    @Override @NonNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @NonNull View view = convertView != null ? convertView : Views.inflateLayout(R.layout.activity_main_location_item, parent);
        @Nullable Cinema cinema = getItem(position);

        if(cinema == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);

            TextView location = view.findViewById(R.id.location);
            location.setText(cinema.getLocation().toLongString(true));
        }

        return view;
    }
}
