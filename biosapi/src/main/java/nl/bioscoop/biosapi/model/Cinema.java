package nl.bioscoop.biosapi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Cinema implements Serializable {
    @ColumnInfo(name = "cinemaID")
    private int ID;
    private @NonNull @Embedded Location location;

    public Cinema(@NonNull JSONObject json) throws JSONException {
        this(json.getInt("id"), new Location(json.getJSONObject("location")));
    }

    public Cinema(int ID, @NonNull Location location) {
        this.ID = ID;
        this.location = location;
    }

    public int getID() {
        return ID;
    }

    @NonNull public Location getLocation() {
        return location;
    }
}
