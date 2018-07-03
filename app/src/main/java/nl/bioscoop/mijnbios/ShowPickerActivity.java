package nl.bioscoop.mijnbios;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.model.Movie;
import nl.bioscoop.biosapi.model.Show;
import nl.bioscoop.biosapi.utils.DataLoader;
import nl.bioscoop.mijnbios.adapters.ShowPickerAdapter;
import nl.bioscoop.mijnbios.utils.Views;

public class ShowPickerActivity extends AppCompatActivity {
    private BiosAPI api;
    private ListView items;
    private ArrayList<Show> shows;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picker);loadActionbar();

        api = new BiosAPI(new DataLoader(this, Config.MAX_CACHE_SIZE_MB), getResources().getString(R.string.languageCode));

        shows = new ArrayList<>();

        items = findViewById(R.id.list);
        items.setAdapter(new ShowPickerAdapter(this, shows));
        items.setOnItemClickListener((adapterView, view, i, l) -> {
            Show show = ((ShowPickerAdapter) adapterView.getAdapter()).getItem(i);

            View v = Views.inflateLayout(R.layout.dialog_number_picker, this);
            NumberPicker numberPicker = v.findViewById(R.id.numberPicker);
            numberPicker.setMinValue(1);
            numberPicker.setValue(1);
            if(show != null && show.getHall().getSeatsHorizontal() != null && show.getHall().getSeatsVertical() != null)
                numberPicker.setMaxValue(show.getHall().getSeatsHorizontal() * show.getHall().getSeatsVertical());

            new AlertDialog.Builder(this)
                    .setTitle(R.string.ticketAmount)
                    .setView(v)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        Intent intent = new Intent(this, TicketConfigActivity.class);
                        intent.putExtra(Config.EXTRA_SHOW, show);
                        intent.putExtra(Config.EXTRA_TICKETAMOUNT, numberPicker.getValue());
                        startActivity(intent);
                    })
                    .show();
        });

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(Config.EXTRA_MOVIE);
        if(movie != null) loadData(movie);
    }

    private void loadActionbar(){
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadData(@NonNull Movie movie){
        setTitle(getResources().getString(R.string.buyTickets) + " (" + movie.getTitle() + ")");

        api.getShowsForMovie(movie, (list) -> {
            shows.clear();
            shows.addAll(list);
            runOnUiThread(((ShowPickerAdapter) items.getAdapter())::notifyDataSetChanged);
        });
    }
}
