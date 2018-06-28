package nl.bioscoop.biosapi.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nl.bioscoop.biosapi.model.Ticket;

public @Dao interface TicketDAO {
    @Query("SELECT * FROM ticket") List<Ticket> getTickets();

    @Insert void insert(Ticket... users);

    @Delete void delete(Ticket user);
}
