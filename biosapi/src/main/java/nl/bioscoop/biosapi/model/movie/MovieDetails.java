package nl.bioscoop.biosapi.model.movie;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MovieDetails extends Movie implements Serializable {
    private @NonNull String title;
    private @NonNull String backdrop;
    private @NonNull String description;

    public MovieDetails(@NonNull JSONObject json) throws JSONException {
        this(json.getInt("id"), json.getString("title"), json.getString("backdrop"), json.getString("overview"));
    }

    public MovieDetails(int id, @NonNull String title, @NonNull String backdrop, @NonNull String description) {
        super(id);
        this.title = title;
        this.backdrop = backdrop;
        this.description = description;
    }

    @NonNull public String getTitle() {
        return title;
    }

    @NonNull public String getBackdrop() {
        return backdrop;
    }

    @NonNull public String getDescription() {
        return description;
    }
}
