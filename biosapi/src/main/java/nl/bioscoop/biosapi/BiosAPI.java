package nl.bioscoop.biosapi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.ValueCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import nl.bioscoop.biosapi.model.Movie;
import nl.bioscoop.biosapi.model.Show;
import nl.bioscoop.biosapi.utils.DataLoader;

public class BiosAPI {
    private @NonNull DataLoader dataLoader;
    private @NonNull String languageCode;
    private static final String API_URL = "https://mijnbios.herokuapp.com/api/v2/";

    public BiosAPI(@NonNull DataLoader dataLoader) {
        this(dataLoader, "en-US");
    }

    public BiosAPI(@NonNull DataLoader dataLoader, @NonNull String languageCode) {
        this.dataLoader = dataLoader;
        this.languageCode = languageCode;
    }

    public void getAllMoviePosters(@NonNull ValueCallback<ArrayList<Movie>> callback){
        dataLoader.load(API_URL + "shows/movies?language=" + languageCode, (responseBody) -> {
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

    @Deprecated public void getMovieDetail(int id, @NonNull ValueCallback<Movie> callback){
        dataLoader.load(API_URL + "movies/" + String.valueOf(id) + "?language=" + languageCode, (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONObject movie = new JSONObject(responseBody);
                callback.onReceiveValue(new Movie(movie));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getShowsForMovie(@NonNull Movie movie, @NonNull ValueCallback<ArrayList<Show>> callback){
        dataLoader.load(API_URL + "movies/" + String.valueOf(movie.getID()) + "/shows?language=" + languageCode, (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONArray showsList = new JSONArray(responseBody);
                ArrayList<Show> shows = new ArrayList<>();

                for (int i = 0; i < showsList.length(); i++){
                    @Nullable JSONObject show = showsList.optJSONObject(i);
                    if(show != null) {
                        try {
                            shows.add(new Show(show, movie));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                callback.onReceiveValue(shows);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getTicketPrice(@NonNull Show show, int amount, @NonNull ValueCallback<Double> callback){
        dataLoader.load(API_URL + "shows/" + String.valueOf(show.getID()) + "/ticketprice?amount=" + amount, (responseBody) -> {
            if(responseBody == null) return;

            try {
                callback.onReceiveValue(new JSONObject(responseBody).getDouble("price"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getSeats(@NonNull Show show, int amount, @NonNull ValueCallback<ArrayList<String>> callback){
        dataLoader.load(API_URL + "shows/" + String.valueOf(show.getID()) + "/tickets?amount=" + amount, DataLoader.Method.PATCH, (responseBody) -> {
            if(responseBody == null) return;

            try {
                JSONArray seatList = new JSONArray(responseBody);
                ArrayList<String> seats = new ArrayList<>();

                for (int i = 0; i < seatList.length(); i++) seats.add(seatList.getString(i));

                callback.onReceiveValue(seats);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
