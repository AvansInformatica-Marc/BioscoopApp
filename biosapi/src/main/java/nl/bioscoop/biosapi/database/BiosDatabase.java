package nl.bioscoop.biosapi.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

public class BiosDatabase {
    private BiosDB db;

    private BiosDatabase(@NonNull Context context) {
        db = Room.databaseBuilder(context, BiosDB.class, "BiosDB").build();
    }

    public BiosDB getDB() {
        return db;
    }

    // Singleton
    private static BiosDatabase INSTANCE = null;

    public static BiosDatabase getInstance(@NonNull Context context) {
        if(INSTANCE == null) INSTANCE = new BiosDatabase(context.getApplicationContext());
        return INSTANCE;
    }
}
