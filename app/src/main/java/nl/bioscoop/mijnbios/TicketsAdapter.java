package nl.bioscoop.mijnbios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.bioscoop.biosapi.model.Ticket;

import static nl.bioscoop.mijnbios.utils.Views.inflateLayout;

public class TicketsAdapter extends ArrayAdapter<Ticket> {
    public TicketsAdapter(@NonNull Context context, @NonNull ArrayList<Ticket> list){
        super(context, 0, list);
    }

    @Override @NonNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @NonNull TextView view = convertView != null && convertView instanceof TextView ? (TextView) convertView : inflateLayout(android.R.layout.simple_list_item_1, parent);
        @Nullable Ticket ticket = getItem(position);

        if(ticket == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(ticket.getSeat() + ") " + ticket.getMovie().getTitle());
        }

        return view;
    }
}
