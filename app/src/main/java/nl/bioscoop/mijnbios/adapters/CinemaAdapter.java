package nl.bioscoop.mijnbios.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
            location.setOnClickListener((v) -> {
                try {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + cinema.getLocation().toLongString(false)));
                    v.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(v.getContext(), "Couldn't find app to open this.", Toast.LENGTH_LONG).show();
                }
            });

            TextView email = view.findViewById(R.id.email);
            email.setText(cinema.getEmail());
            email.setOnClickListener((v) -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", cinema.getEmail(), null));
                    v.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(v.getContext(), "Couldn't find app to open this.", Toast.LENGTH_LONG).show();
                }
            });

            TextView phone = view.findViewById(R.id.phone);
            phone.setText(cinema.getPhone());
            phone.setOnClickListener((v) -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + cinema.getPhone()));
                    v.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(v.getContext(), "Couldn't find app to open this.", Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }
}
