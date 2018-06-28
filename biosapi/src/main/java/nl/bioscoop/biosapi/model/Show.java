package nl.bioscoop.biosapi.model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Show implements Serializable {
    private int id;
    private int hallID;
    private int cinemaID;
    private Date datetime;
    private @NonNull String location;

    public Show(@NonNull JSONObject json) throws JSONException, ParseException {
        this(
                json.getInt("hallID"),
                json.getInt("showID"),
                json.getInt("cinemaID"),
                json.getString("datetime"),
                json.getJSONObject("location").getString("street") + ", " + json.getJSONObject("location").getString("city")
        );
    }

    public Show(int showID, int hallID, int cinemaID, @NonNull String datetime, @NonNull String location) throws ParseException {
        this.hallID = hallID;
        this.id = showID;
        this.cinemaID = cinemaID;
        this.location = location;
        this.datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK).parse(datetime);
    }

    public int getHallID() {
        return hallID;
    }

    public int getID() {
        return id;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public Date getDatetime() {
        return datetime;
    }

    @NonNull public String getLocation() {
        return location;
    }
}