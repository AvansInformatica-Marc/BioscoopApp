package nl.bioscoop.biosapi.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

public @Entity class Ticket implements Serializable {
    @PrimaryKey private int ID;
    private @NonNull String seat;
    private @Embedded @NonNull Show show;

    @Ignore public Ticket(@NonNull String seat, @NonNull Show show) {
        this(seat.hashCode() * show.getID(), seat, show);
    }

    public Ticket(int ID, @NonNull String seat, @NonNull Show show) {
        this.ID = ID;
        this.seat = seat;
        this.show = show;
    }

    public int getID() {
        return ID;
    }

    public @NonNull String getSeat() {
        return seat;
    }

    public @NonNull Show getShow() {
        return show;
    }
}
