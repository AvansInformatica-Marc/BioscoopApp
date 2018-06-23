package nl.bioscoop.mijnbios;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.ValueCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.bioscoop.mijnbios.model.Movie;
import nl.bioscoop.mijnbios.utils.DataLoader;

public class Api {
    private @NonNull DataLoader dataLoader;

    public Api(@NonNull DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public void getAllMovies(ValueCallback<ArrayList<Movie>> callback){
        dataLoader.load("https://mijnbios.herokuapp.com/api/v1/movies", (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONArray moviesList = new JSONArray(responseBody);
                ArrayList<Movie> movies = new ArrayList<>();

                for (int i = 0; i < moviesList.length(); i++){
                    @Nullable JSONObject movie = moviesList.optJSONObject(i);
                    if(movie != null) movies.add(new Movie(movie));
                }

                callback.onReceiveValue(movies);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
