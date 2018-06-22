package nl.bioscoop.mijnbios;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();
    }

    private void showAllMovies() {
        Request request = new Request.Builder().url("").build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(final Call call, IOException e) {
                // Error

                runOnUiThread(() -> {
                    // For the example, you can show an error dialog or a toast
                    // on the main UI thread
                });
            }

            @Override public void onResponse(Call call, final Response response) throws IOException {
                String res = response.body().string();

                // Do something with the response
            }
        });
    }
}
