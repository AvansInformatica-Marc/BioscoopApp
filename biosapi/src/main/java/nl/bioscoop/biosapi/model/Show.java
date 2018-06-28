package nl.bioscoop.biosapi.model;

import android.arch.persistence.room.ColumnInfo;
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

    public Show(int id, int hallID, int cinemaID, @NonNull String datetime, @NonNull String location) throws ParseException {
        this(id, hallID, cinemaID, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK).parse(datetime), location);
    }

    public Show(int id, int hallID, int cinemaID, Date datetime, @NonNull String location) {
        this.id = id;
        this.hallID = hallID;
        this.cinemaID = cinemaID;
        this.datetime = datetime;
        this.location = location;
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

    /* ONLY FOR DATABASE */ public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    /* ONLY FOR DATABASE */  public int getId() {
        return getID();
    }
}