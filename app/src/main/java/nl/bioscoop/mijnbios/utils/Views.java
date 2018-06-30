package nl.bioscoop.mijnbios.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class Views {
    @NonNull public static <T extends View> T inflateLayout(@LayoutRes int layout, Context context){
        return (T) LayoutInflater.from(context).inflate(layout, null, false);
    }

    /**
     * Inflates the given layout.
     * @param layout The resource ID of the layout to add.
     * @param parent The future parent of the layout.
     * @return The layout as view.
     */
    @NonNull public static <T extends View> T inflateLayout(@LayoutRes int layout, @NonNull ViewGroup parent){
        return (T) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }
}
