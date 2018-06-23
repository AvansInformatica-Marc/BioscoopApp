package nl.bioscoop.mijnbios;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;

public class MovieDetailActivity extends Activity {
    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }
}
