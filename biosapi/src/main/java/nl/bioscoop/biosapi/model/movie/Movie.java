package nl.bioscoop.biosapi.model.movie;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;

    public Movie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}