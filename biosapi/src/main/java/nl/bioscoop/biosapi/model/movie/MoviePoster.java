package nl.bioscoop.biosapi.model.movie;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MoviePoster extends Movie implements Serializable {
    private @NonNull String poster;

    public MoviePoster(@NonNull JSONObject json) throws JSONException {
        this(json.getInt("id"), json.getString("title"), json.getString("poster"));
    }

    public MoviePoster(int id, @NonNull String title, @NonNull String poster) {
        super(id, title);
        this.poster = poster;
    }

    @NonNull public String getPoster() {
        return poster;
    }
}
