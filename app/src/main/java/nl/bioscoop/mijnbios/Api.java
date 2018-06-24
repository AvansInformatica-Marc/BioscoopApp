package nl.bioscoop.mijnbios;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.ValueCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.bioscoop.mijnbios.model.movie.MovieDetails;
import nl.bioscoop.mijnbios.model.movie.MoviePoster;
import nl.bioscoop.mijnbios.utils.DataLoader;

public class Api {
    private @NonNull DataLoader dataLoader;

    public Api(@NonNull DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public void getAllMoviePosters(ValueCallback<ArrayList<MoviePoster>> callback){
        dataLoader.load("https://mijnbios.herokuapp.com/api/v1/shows/movies?displayType=poster", (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONArray moviesList = new JSONArray(responseBody);
                ArrayList<MoviePoster> movies = new ArrayList<>();

                for (int i = 0; i < moviesList.length(); i++){
                    @Nullable JSONObject movie = moviesList.optJSONObject(i);
                    if(movie != null) movies.add(new MoviePoster(movie));
                }

                callback.onReceiveValue(movies);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getMovieDetail(int id, ValueCallback<MovieDetails> callback){
        dataLoader.load("https://mijnbios.herokuapp.com/api/v1/movies/" + String.valueOf(id) + "?displayType=details", (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONObject movie = new JSONObject(responseBody);
                callback.onReceiveValue(new MovieDetails(movie));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
