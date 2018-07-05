package nl.bioscoop.mijnbios;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import java.text.DateFormat;
import java.util.Locale;

import nl.bioscoop.biosapi.model.Ticket;
import nl.bioscoop.mijnbios.utils.DateTime;

public class TicketDetailActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        Intent intent = getIntent();
        Ticket ticket = (Ticket) intent.getSerializableExtra(Config.EXTRA_TICKET);
        if(ticket != null) loadData(ticket);
    }

    public void loadData(@NonNull Ticket ticket){
        String qrString = "L" + ticket.getShow().getHall().getCinema().getLocation().toShortString() + "|H" +
                ticket.getShow().getHall().getName() + "|DT" +
                ticket.getShow().getDatetime().getTime() + "|M" +
                ticket.getShow().getMovie().getTitle() + "|S" +
                ticket.getSeat();
        Bitmap qrBitmap = QRCode.from(qrString).withSize(500, 500).withColor(0xFF000000, 0x00000000).bitmap();
        ImageView qrCode = findViewById(R.id.qrCode);
        qrCode.setImageBitmap(qrBitmap);

        TextView movie = findViewById(R.id.movie);
        movie.setText(ticket.getShow().getMovie().getTitle());

        TextView seats = findViewById(R.id.seats);
        seats.setText(ticket.getSeat().replaceAll(",", ", "));

        TextView location = findViewById(R.id.location);
        location.setText(getResources().getString(R.string.hall) + " " + ticket.getShow().getHall().getName() + "\n" +
                ticket.getShow().getHall().getCinema().getLocation().toLongString(true));

        TextView datetime = findViewById(R.id.datetime);
        datetime.setText(DateTime.format(ticket.getShow().getDatetime(), DateFormat.MEDIUM, DateFormat.SHORT, " - ", true, Locale.getDefault()));
    }
}
