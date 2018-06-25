package nl.bioscoop.biosapi.model.movie;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private @NonNull String title;

    public Movie(int id, @NonNull String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    @NonNull public String getTitle() {
        return title;
    }
}