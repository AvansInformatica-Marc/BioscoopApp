package nl.bioscoop.mijnbios;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Locale;

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.database.BiosDatabase;
import nl.bioscoop.biosapi.database.TicketDAO;
import nl.bioscoop.biosapi.model.Show;
import nl.bioscoop.biosapi.model.Ticket;
import nl.bioscoop.biosapi.utils.DataLoader;
import nl.bioscoop.mijnbios.utils.DateTime;
import nl.bioscoop.mijnbios.utils.Images;
import nl.bioscoop.mijnbios.utils.Views;

import static nl.bioscoop.mijnbios.utils.Async.async;

public class TicketConfigActivity extends AppCompatActivity {
    private BiosAPI api;
    private Show show;
    private int ticketAmount;
    private AlertDialog alertDialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_config);

        api = new BiosAPI(new DataLoader(this, Config.MAX_CACHE_SIZE_MB), getResources().getString(R.string.languageCode));

        Intent intent = getIntent();
        show = (Show) intent.getSerializableExtra(Config.EXTRA_SHOW);
        ticketAmount = intent.getIntExtra(Config.EXTRA_TICKETAMOUNT, 1);
        if(show != null) loadData(show, ticketAmount);
    }

    public void loadData(@NonNull Show show, int ticketAmount){
        TextView ticketAmountView = findViewById(R.id.ticketAmount);
        ticketAmountView.setText(String.valueOf(ticketAmount));

        TextView location = findViewById(R.id.location);
        location.setText(show.getHall().getCinema().getLocation().toShortString());

        TextView datetime = findViewById(R.id.datetime);
        datetime.setText(DateTime.format(show.getDatetime(), DateFormat.MEDIUM, DateFormat.SHORT, " - ", true, Locale.getDefault()));

        TextView movieTitle = findViewById(R.id.movieTitle);
        movieTitle.setText(show.getMovie().getTitle());

        if (show.getMovie().getHeaderImage() != null)
            Images.loadImage(show.getMovie().getHeaderImage(), findViewById(R.id.movieBackdrop));

        TextView priceView = findViewById(R.id.price);
        priceView.setText(R.string.loading);
        api.getTicketPrice(show, ticketAmount, (price) -> runOnUiThread(() -> priceView.setText(getPriceDisplayString(price))));
    }

    private String getPriceDisplayString(double price){
        String priceString = String.valueOf(price).replace(".", ",");
        return "€" + (price % 1 == 0 ? (priceString.contains(",") ? priceString.split(",")[0] : priceString) + ",-" : priceString);
    }

    private AlertDialog createLoadingDialog(){
        return new AlertDialog.Builder(this).setView(Views.inflateLayout(R.layout.dialog_loading, this)).show();
    }

    public void buyTickets(View v){
        AlertDialog dialog = createLoadingDialog();
        api.getTicketPrice(show, ticketAmount, (price) -> runOnUiThread(() -> {
            dialog.dismiss();
            LinearLayout dummyPaymentView = Views.inflateLayout(R.layout.dialog_dummy_payment, this);

            TextView title = dummyPaymentView.findViewById(R.id.title);
            title.setText(getResources().getString(R.string.movieTickets) + " (" + getString(R.string.app_name) + ")");

            TextView priceView = dummyPaymentView.findViewById(R.id.price);
            priceView.setText(getPriceDisplayString(price));

            alertDialog = new AlertDialog.Builder(this).setView(dummyPaymentView).show();
        }));
    }

    public void onPaymentConfirmed(View v){
        alertDialog.dismiss();
        AlertDialog dialog = createLoadingDialog();
        api.getSeats(show, ticketAmount, (seats) -> {
            if(seats.size() == 0){
                dialog.dismiss();
                new AlertDialog.Builder(this).setTitle(R.string.oops)
                        .setMessage(R.string.ticketReservationCancelledNoFreeSeats)
                        .show();
            } else {
                StringBuilder seatStringBuilder = new StringBuilder();
                for(String seat : seats) seatStringBuilder.append(seat).append(",");
                String seatString = seatStringBuilder.toString();
                seatString = seatString.substring(0, seatString.length() - 1);
                Ticket ticket = new Ticket(seatString, show);
                /*Ticket[] tickets = new Ticket[seats.size()];
                for(int i = 0; i < seats.size(); i++)
                    tickets[i] = new Ticket(seats.get(i), show);*/

                TicketDAO ticketDAO = BiosDatabase.getInstance(this).getDB().ticketDAO();
                async(() -> {
                    ticketDAO.insert(ticket);
                    runOnUiThread(() -> {
                        dialog.dismiss();
                        Intent intent = new Intent(TicketConfigActivity.this, MainActivity.class);
                        intent.putExtra(Config.EXTRA_TABID, 2);
                        startActivity(intent);
                        finish();
                    });
                });
            }
        });
    }
}
