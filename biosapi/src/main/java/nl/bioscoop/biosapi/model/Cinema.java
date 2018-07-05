package nl.bioscoop.biosapi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Cinema implements Serializable {
    @ColumnInfo(name = "cinemaID")
    private int ID;
    private @NonNull @Embedded Location location;
    private @Nullable @Ignore String email;
    private @Nullable @Ignore String phone;

    public Cinema(@NonNull JSONObject json) throws JSONException {
        this(json.getInt("id"), new Location(json.getJSONObject("location")), json.getString("email"), json.getString("phonenumber"));
    }

    public Cinema(int ID, @NonNull Location location) {
        this(ID, location, null, null);
    }

    public Cinema(int ID, @NonNull Location location, @Nullable String email, @Nullable String phone) {
        this.ID = ID;
        this.location = location;
        this.email = email;
        this.phone = phone;
    }

    public int getID() {
        return ID;
    }

    @NonNull public Location getLocation() {
        return location;
    }

    public @Nullable String getEmail() {
        return email;
    }

    public @Nullable String getPhone() {
        return phone;
    }
}
