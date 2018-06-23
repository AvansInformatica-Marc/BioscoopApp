package nl.bioscoop.mijnbios;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nl.bioscoop.mijnbios.model.Movie;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends Activity {
    private OkHttpClient client;
    private GridView movieGrid;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieGrid = findViewById(R.id.grid);
        client = new OkHttpClient.Builder()
                .cache(new Cache(new File(getCacheDir(), "http"), 10 * 1024 * 1024))
                .build();

        showAllMovies();
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    private void showAllMovies(){ showAllMovies(null); }

    @RequiresPermission(Manifest.permission.INTERNET)
    private void showAllMovies(@Nullable CacheControl cacheControl) {
        Request.Builder defaultRequest = new Request.Builder().url("https://mijnbios.herokuapp.com/api/v1/movies");
        if(cacheControl != null) defaultRequest.cacheControl(cacheControl);
        Request request = defaultRequest.build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@Nullable final Call call, @Nullable IOException e) {
                if(cacheControl == null) showAllMovies(CacheControl.FORCE_CACHE);
            }

            @Override public void onResponse(@Nullable Call call, @Nullable final Response response) throws IOException {
                ResponseBody responseBody = response != null ? response.body() : null;
                String responseString = responseBody != null ? responseBody.string() : null;
                onMoviesFetched(responseString);
            }
        });
    }

    private void onMoviesFetched(@Nullable String responseBody){
        if(responseBody == null) return;

        try {
            JSONArray moviesList = new JSONArray(responseBody);
            ArrayList<Movie> movies = new ArrayList<>();
            for (int i = 0; i < moviesList.length(); i++){
                @Nullable JSONObject movie = moviesList.optJSONObject(i);
                if(movie != null)
                    movies.add(new Movie(movie.getInt("id"), movie.getString("name"), movie.getString("description"), movie.getInt("minAge"), movie.getInt("duration"), movie.getString("poster")));
            }
            runOnUiThread(() -> movieGrid.setAdapter(new MovieAdapter(MainActivity.this, movies)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
