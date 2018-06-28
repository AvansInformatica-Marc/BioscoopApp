package nl.bioscoop.biosapi.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import nl.bioscoop.biosapi.model.Ticket;

@Database(entities = {Ticket.class}, version = 1)
public abstract class BiosDB extends RoomDatabase {
    public abstract TicketDAO ticketDAO();
}