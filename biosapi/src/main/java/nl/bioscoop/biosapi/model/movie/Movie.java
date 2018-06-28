package nl.bioscoop.biosapi.model.movie;

import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Movie implements Serializable {
    @ColumnInfo(name = "movieID")
    private int id;
    private @NonNull String title;

    public Movie(int id, @NonNull String title) {
        this.id = id;
        this.title = title;
    }

    @Deprecated public int getId() {
        return id;
    }

    public int getID() {
        return id;
    }

    @NonNull public String getTitle() {
        return title;
    }
}