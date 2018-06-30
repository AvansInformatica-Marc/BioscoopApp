package nl.bioscoop.mijnbios.adapters;

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

import nl.bioscoop.biosapi.model.Show;
import nl.bioscoop.mijnbios.R;
import nl.bioscoop.mijnbios.utils.DateTime;

import static nl.bioscoop.mijnbios.utils.Views.inflateLayout;

public class ShowPickerAdapter extends ArrayAdapter<Show> {
    public ShowPickerAdapter(@NonNull Context context, @NonNull ArrayList<Show> list){
        super(context, 0, list);
    }

    @Override @NonNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @NonNull View view = convertView != null ? convertView : inflateLayout(R.layout.activity_show_picker_item, parent);
        @Nullable Show show = getItem(position);

        if(show == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            View card = view.findViewById(R.id.card);
            card.setOnClickListener(view1 -> ((ListView) parent).performItemClick(convertView, position, position));

            TextView datetime = view.findViewById(R.id.datetime);
            datetime.setText(DateTime.format(show.getDatetime(), DateFormat.MEDIUM, DateFormat.SHORT, " - ", true, Locale.getDefault()));

            TextView location = view.findViewById(R.id.location);
            location.setText(show.getLocation());
        }

        return view;
    }
}
