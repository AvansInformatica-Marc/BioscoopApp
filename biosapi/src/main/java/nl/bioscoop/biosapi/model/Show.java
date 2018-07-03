package nl.bioscoop.biosapi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Show implements Serializable {
    @ColumnInfo(name = "showID")
    private int ID;
    private @NonNull @Embedded Movie movie;
    private @NonNull @Embedded Hall hall;
    private @NonNull Date datetime;

    public Show(@NonNull JSONObject json, @NonNull Movie movie) throws JSONException, ParseException {
        this(
                json.getInt("id"),
                movie,
                new Hall(json.getJSONObject("hall")),
                json.getString("datetime")
        );
    }

    public Show(int ID, @NonNull Movie movie, @NonNull Hall hall, @NonNull String datetime) throws ParseException {
        this(ID, movie, hall, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK).parse(datetime));
    }

    public Show(int ID, @NonNull Movie movie, @NonNull Hall hall, @NonNull Date datetime) {
        this.ID = ID;
        this.movie = movie;
        this.hall = hall;
        this.datetime = datetime;
    }

    public int getID() {
        return ID;
    }

    public @NonNull Movie getMovie() {
        return movie;
    }

    public @NonNull Hall getHall() {
        return hall;
    }

    public @NonNull Date getDatetime() {
        return datetime;
    }
}
