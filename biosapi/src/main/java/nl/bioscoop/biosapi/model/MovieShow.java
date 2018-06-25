package nl.bioscoop.biosapi.model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MovieShow implements Serializable {
    private int showID;
    private int hallID;
    private int cinemaID;
    private @NonNull String datetime;
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

    public MovieShow(int hallID, int showID, int cinemaID, @NonNull String datetime, @NonNull String location) {
        this.hallID = hallID;
        this.showID = showID;
        this.cinemaID = cinemaID;
        this.datetime = datetime;
        this.location = location;
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

    @NonNull public String getDatetime() {
        return datetime;
    }

    @NonNull public String getLocation() {
        return location;
    }
}
