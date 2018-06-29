package nl.bioscoop.mijnbios;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Locale;

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.database.BiosDatabase;
import nl.bioscoop.biosapi.database.TicketDAO;
import nl.bioscoop.biosapi.model.MovieShow;
import nl.bioscoop.biosapi.model.Ticket;
import nl.bioscoop.biosapi.model.movie.Movie;
import nl.bioscoop.biosapi.model.movie.MovieDetails;
import nl.bioscoop.biosapi.utils.DataLoader;
import nl.bioscoop.mijnbios.utils.DateTime;
import nl.bioscoop.mijnbios.utils.Images;
import nl.bioscoop.mijnbios.utils.Views;

import static nl.bioscoop.mijnbios.utils.Async.async;

public class TicketConfigActivity extends AppCompatActivity {
    private BiosAPI api;
    private Movie movie;
    private MovieShow movieShow;
    private AlertDialog alertDialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_config);

        api = new BiosAPI(new DataLoader(this, Config.MAX_CACHE_SIZE_MB), getResources().getString(R.string.languageCode));

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(Config.EXTRA_MOVIE);
        movieShow = (MovieShow) intent.getSerializableExtra(Config.EXTRA_MOVIESHOW);
        if(movie != null && movieShow != null) loadData(movie, movieShow);
    }

    public void loadData(@NonNull Movie movie, @NonNull MovieShow movieShow){
        TextView location = findViewById(R.id.location);
        location.setText(movieShow.getLocation());

        TextView datetime = findViewById(R.id.datetime);
        datetime.setText(DateTime.format(movieShow.getDatetime(), DateFormat.MEDIUM, DateFormat.SHORT, " - ", true, Locale.getDefault()));

        TextView movieTitle = findViewById(R.id.movieTitle);
        movieTitle.setText(movie.getTitle());

        if(movie instanceof MovieDetails){
            MovieDetails movieDetails = (MovieDetails) movie;
            Images.loadImage(movieDetails.getBackdrop(), findViewById(R.id.movieBackdrop));
        }
    }

    public void buyTickets(View v){
        LinearLayout dummyPaymentView = Views.inflateLayout(R.layout.dummy_payment, this);

        TextView title = dummyPaymentView.findViewById(R.id.title);
        title.setText("Movie tickets (" + getString(R.string.app_name) + ")");

        alertDialog = new AlertDialog.Builder(this)
                .setView(dummyPaymentView)
                .show();
    }

    public void onPaymentConfirmed(View v){
        alertDialog.dismiss();

        Ticket ticket = new Ticket("1", movie, movieShow);
        TicketDAO ticketDAO = BiosDatabase.getInstance(this).getDB().ticketDAO();
        async(() -> {
            ticketDAO.insert(ticket);
            return ticketDAO.getTickets().size();
        }, (amountOfTickets) -> {
            Log.e("TicketAmount", "Amount: " + amountOfTickets);
        });
    }
}
