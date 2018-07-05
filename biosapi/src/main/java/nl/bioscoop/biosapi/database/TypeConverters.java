package nl.bioscoop.biosapi.database;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import java.util.Date;

// Used to convert types between Java Objects and types the Database can handle.
public class TypeConverters {
    @Nullable @TypeConverter public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @Nullable @TypeConverter public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
