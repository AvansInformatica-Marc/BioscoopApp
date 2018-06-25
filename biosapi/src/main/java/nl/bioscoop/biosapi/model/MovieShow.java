package nl.bioscoop.biosapi.model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieShow implements Serializable {
    private int showID;
    private int hallID;
    private int cinemaID;
    private Date datetime;
    private @NonNull String location;

    public MovieShow(@NonNull JSONObject json) throws JSONException {
        this(
                json.getInt("hallID"),
                json.getInt("showID"),
                json.getInt("cinemaID"),
                json.getString("datetime"),
                json.getJSONObject("location").getString("street") + ", " + json.getJSONObject("location").getString("city")
        );
    }

    public MovieShow(int showID, int hallID, int cinemaID, @NonNull String datetime, @NonNull String location) {
        this.hallID = hallID;
        this.showID = showID;
        this.cinemaID = cinemaID;
        this.location = location;

        try {
            this.datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getHallID() {
        return hallID;
    }

    public int getShowID() {
        return showID;
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
