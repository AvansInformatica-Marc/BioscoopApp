package nl.bioscoop.biosapi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.ValueCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.bioscoop.biosapi.model.MovieShow;
import nl.bioscoop.biosapi.model.movie.MovieDetails;
import nl.bioscoop.biosapi.model.movie.MoviePoster;
import nl.bioscoop.biosapi.utils.DataLoader;

public class BiosAPI {
    private @NonNull DataLoader dataLoader;
    private @NonNull String languageCode;
    private static final String API_URL = "https://mijnbios.herokuapp.com/api/v1/";

    public BiosAPI(@NonNull DataLoader dataLoader) {
        this(dataLoader, "en-US");
    }

    public BiosAPI(@NonNull DataLoader dataLoader, @NonNull String languageCode) {
        this.dataLoader = dataLoader;
        this.languageCode = languageCode;
    }

    public void getAllMoviePosters(@NonNull ValueCallback<ArrayList<MoviePoster>> callback){
        dataLoader.load(API_URL + "shows/movies?displayType=poster&language=" + languageCode, (responseBody) -> {
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

    public void getMovieDetail(int id, @NonNull ValueCallback<MovieDetails> callback){
        dataLoader.load(API_URL + "movies/" + String.valueOf(id) + "?displayType=details&language=" + languageCode, (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONObject movie = new JSONObject(responseBody);
                callback.onReceiveValue(new MovieDetails(movie));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getShowsForMovie(int id, @NonNull ValueCallback<ArrayList<MovieShow>> callback){
        dataLoader.load(API_URL + "movies/" + String.valueOf(id) + "/shows?language=" + languageCode, (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONArray showsList = new JSONArray(responseBody);
                ArrayList<MovieShow> shows = new ArrayList<>();

                for (int i = 0; i < showsList.length(); i++){
                    @Nullable JSONObject show = showsList.optJSONObject(i);
                    if(show != null) shows.add(new MovieShow(show));
                }

                callback.onReceiveValue(shows);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
