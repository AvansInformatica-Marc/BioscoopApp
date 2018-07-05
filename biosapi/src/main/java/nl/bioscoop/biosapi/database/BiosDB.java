package nl.bioscoop.biosapi.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import nl.bioscoop.biosapi.model.Ticket;

@Database(entities = {Ticket.class}, version = 2)
@TypeConverters({nl.bioscoop.biosapi.database.TypeConverters.class})
public abstract class BiosDB extends RoomDatabase {
    public abstract TicketDAO ticketDAO();
}