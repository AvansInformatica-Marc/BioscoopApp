package nl.bioscoop.biosapi.model;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

@Deprecated public class MovieShow extends Show implements Serializable {
    public MovieShow(@NonNull JSONObject json) throws JSONException, ParseException {
        super(json);
    }

    public MovieShow(int showID, int hallID, int cinemaID, @NonNull String datetime, @NonNull String location) throws ParseException {
        super(showID, hallID, cinemaID, datetime, location);
    }

    @Deprecated public int getShowID() {
        return getID();
    }
}
