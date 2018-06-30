package nl.bioscoop.mijnbios.utils;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTime {
    public static String format(@NonNull Date date, int dateFormat, int timeFormat, @NonNull String separator, boolean dateFirst, @NonNull Locale locale){
        String dateText = DateFormat.getDateInstance(dateFormat, locale).format(date);
        String timeText = DateFormat.getTimeInstance(timeFormat, locale).format(date);

        return dateFirst ? dateText + separator + timeText : timeText + separator + dateText;
    }
}
