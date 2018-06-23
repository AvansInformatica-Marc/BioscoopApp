package nl.bioscoop.mijnbios.model;

import android.support.annotation.NonNull;

public class Movie {
    private int id;
    private @NonNull String title;
    private @NonNull String description;
    private int minAge;
    private int duration;
    private @NonNull String poster;

    public Movie(int id, @NonNull String title, @NonNull String description, int minAge, int duration, @NonNull String poster) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.minAge = minAge;
        this.duration = duration;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    @NonNull public String getTitle() {
        return title;
    }

    @NonNull public String getDescription() {
        return description;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getDuration() {
        return duration;
    }

    @NonNull public String getPoster() {
        return poster;
    }
}