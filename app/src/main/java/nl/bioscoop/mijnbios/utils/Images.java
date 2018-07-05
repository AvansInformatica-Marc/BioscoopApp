package nl.bioscoop.mijnbios.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public final class Images {
    public static void loadImage(@NonNull String url, @NonNull ImageView imageView){
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
}
