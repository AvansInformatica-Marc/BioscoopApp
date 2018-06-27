package nl.bioscoop.mijnbios.utils;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Views {
    @NonNull public static <T extends View> T inflateLayout(@LayoutRes int layout){
        return inflateLayout(layout, null);
    }

    /**
     * Inflates the given layout.
     * @param layout The resource ID of the layout to add.
     * @param parent The future parent of the layout.
     * @return The layout as view.
     */
    @NonNull public static <T extends View> T inflateLayout(@LayoutRes int layout, @Nullable ViewGroup parent){
        return (T) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }
}
