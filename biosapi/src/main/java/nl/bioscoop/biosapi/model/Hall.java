package nl.bioscoop.biosapi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Hall implements Serializable {
    @ColumnInfo(name = "hallID")
    private int ID;
    private @NonNull @Embedded Cinema cinema;
    private @NonNull String name;
    private @Ignore @Nullable Integer seatsHorizontal;
    private @Ignore @Nullable Integer seatsVertical;

    public Hall(@NonNull JSONObject json) throws JSONException {
        this(
                json.getInt("id"),
                new Cinema(json.getJSONObject("cinema")),
                json.getString("name"),
                json.getJSONObject("seats").getInt("horizontal"),
                json.getJSONObject("seats").getInt("vertical")
        );
    }

    public Hall(int ID, @NonNull Cinema cinema, @NonNull String name) {
        this(ID, cinema, name, null, null);
    }

    public Hall(int ID, @NonNull Cinema cinema, @NonNull String name, @Nullable Integer seatsHorizontal, @Nullable Integer seatsVertical) {
        this.ID = ID;
        this.cinema = cinema;
        this.name = name;
        this.seatsHorizontal = seatsHorizontal;
        this.seatsVertical = seatsVertical;
    }

    public int getID() {
        return ID;
    }

    public @NonNull Cinema getCinema() {
        return cinema;
    }

    public @NonNull String getName() {
        return name;
    }

    public @Nullable Integer getSeatsHorizontal() {
        return seatsHorizontal;
    }

    public @Nullable Integer getSeatsVertical() {
        return seatsVertical;
    }
}
