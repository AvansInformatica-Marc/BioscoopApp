package nl.bioscoop.mijnbios.utils;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.webkit.ValueCallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DataLoader {
    private @NonNull OkHttpClient client;

    public DataLoader(@NonNull Context context){
        this(new OkHttpClient.Builder()
                .cache(new Cache(new File(context.getCacheDir(), "http"), 10 * 1024 * 1024))
                .build());
    }

    public DataLoader(@NonNull OkHttpClient client) {
        this.client = client;
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    private void load(@NonNull String url, @Nullable CacheControl cacheControl, @NonNull ValueCallback<String> callback) {
        Request.Builder defaultRequest = new Request.Builder().url(url);
        if(cacheControl != null) defaultRequest.cacheControl(cacheControl);
        Request request = defaultRequest.build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@Nullable final Call call, @Nullable IOException e) {
                if(cacheControl == null) load(url, CacheControl.FORCE_CACHE, callback);
            }

            @Override public void onResponse(@Nullable Call call, @Nullable final Response response) throws IOException {
                ResponseBody responseBody = response != null ? response.body() : null;
                String responseString = responseBody != null ? responseBody.string() : null;
                callback.onReceiveValue(responseString);
            }
        });
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    public void load(@NonNull String url, @NonNull ValueCallback<String> callback){
        load(url, null, callback);
    }
}
