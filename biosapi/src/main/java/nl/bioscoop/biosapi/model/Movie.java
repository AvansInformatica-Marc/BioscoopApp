package nl.bioscoop.biosapi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Movie implements Serializable {
    @ColumnInfo(name = "movieID")
    private int ID;
    private @NonNull String title;
    private @Nullable @Ignore String posterImage;
    private @Nullable @Ignore String headerImage;
    private @Nullable @Ignore String description;

    public Movie(@NonNull JSONObject json) throws JSONException {
        this(
                json.getInt("id"),
                json.getString("title"),
                json.getJSONObject("images").getString("poster"),
                json.getJSONObject("images").getString("header"),
                json.getString("description")
        );
    }

    public Movie(int ID, @NonNull String title) {
        this(ID, title, null, null, null);
    }

    public Movie(int ID, @NonNull String title, @Nullable String posterImage, @Nullable String headerImage, @Nullable String description) {
        this.ID = ID;
        this.title = title;
        this.posterImage = posterImage;
        this.headerImage = headerImage;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    @NonNull public String getTitle() {
        return title;
    }

    @Nullable public String getPosterImage() {
        return posterImage;
    }

    @Nullable public String getHeaderImage() {
        return headerImage;
    }

    @Nullable public String getDescription() {
        return description;
    }
}