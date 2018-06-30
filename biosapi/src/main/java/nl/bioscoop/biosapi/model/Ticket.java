package nl.bioscoop.biosapi.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import nl.bioscoop.biosapi.model.movie.Movie;

@Entity
public class Ticket {
    @PrimaryKey private int id;
    private @NonNull String seat;
    @Embedded private Movie movie;
    @Embedded private Show show;

    public Ticket(@NonNull String seat, Movie movie, Show show) {
        this.seat = seat;
        this.movie = new Movie(movie.getID(), movie.getTitle()); // Don't save data like image urls or descriptions into the DB
        this.show = show;
        this.id = seat.hashCode() * movie.getID() * show.getID();
    }

    public int getId() {
        return id;
    }

    @NonNull public String getSeat() {
        return seat;
    }

    public Movie getMovie() {
        return movie;
    }

    public Show getShow() {
        return show;
    }

    /* ONLY FOR DATABASE */  public void setId(int id) {
        this.id = id;
    }
}
