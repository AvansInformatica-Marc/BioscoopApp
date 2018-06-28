package nl.bioscoop.biosapi.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

public class BiosDatabase {
    private RoomDatabase db;

    private BiosDatabase(@NonNull Context context) {
        db = Room.databaseBuilder(context, BiosDB.class, "BiosDB").build();
    }

    public RoomDatabase getDB() {
        return db;
    }

    // Singleton
    private static BiosDatabase INSTANCE = null;

    public static BiosDatabase getInstance(@NonNull Context context) {
        if(INSTANCE == null) new BiosDatabase(context.getApplicationContext());
        return INSTANCE;
    }
}
