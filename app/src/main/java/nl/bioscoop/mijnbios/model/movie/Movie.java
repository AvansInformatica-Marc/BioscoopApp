package nl.bioscoop.mijnbios.model.movie;

import java.io.Serializable;

public abstract class Movie implements Serializable {
    private int id;

    public Movie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}