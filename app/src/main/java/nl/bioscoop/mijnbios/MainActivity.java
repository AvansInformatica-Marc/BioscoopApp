package nl.bioscoop.mijnbios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.model.movie.MoviePoster;
import nl.bioscoop.biosapi.utils.DataLoader;

public class MainActivity extends AppCompatActivity {
    private BiosAPI api;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Tab[] tabs;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(true);

        api = new BiosAPI(new DataLoader(this, Config.MAX_CACHE_SIZE_MB), getResources().getString(R.string.languageCode));

        tabs = new Tab[]{
                new MoviesTab(),
                new LocationsTab(),
                new TicketsTab()
        };

        switchTab(0);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int position = menuItem.getItemId() == R.id.movies ? 0 :
                            menuItem.getItemId() == R.id.locations ? 1 :
                            menuItem.getItemId() == R.id.tickets ? 2 : -1;
            switchTab(position);
            return position != -1;
        });
    }

    public void switchTab(int position){
        tabs[0].onHide();
        tabs[1].onHide();
        tabs[2].onHide();

        tabs[position].onShow();
    }

    abstract class Tab<T> {
        protected ArrayList<T> list;
        protected AbsListView listView;

        public Tab(int listViewId){
            list = new ArrayList<>();

            listView = findViewById(listViewId);
        }

        public void onShow(){
            listView.setVisibility(View.VISIBLE);
            loadData();
        }

        abstract void loadData();

        public void onHide(){
            listView.setVisibility(View.GONE);
        }
    }

    class MoviesTab extends Tab<MoviePoster> {
        public MoviesTab(){
            super(R.id.movieGrid);

            listView.setAdapter(new MovieAdapter(MainActivity.this, list));
            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                MoviePoster movie = ((MovieAdapter) listView.getAdapter()).getItem(i);
                if(movie != null) {
                    Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                    intent.putExtra(Config.EXTRA_MOVIE, movie);
                    startActivity(intent);
                }
            });
        }

        public void loadData(){
            swipeRefreshLayout.setRefreshing(true);
            api.getAllMoviePosters((moviesList) -> {
                list.clear();
                list.addAll(moviesList);
                runOnUiThread(((MovieAdapter) listView.getAdapter())::notifyDataSetChanged);
                swipeRefreshLayout.setRefreshing(false);
            });
        }
    }

    class LocationsTab extends Tab<Object> {
        public LocationsTab(){
            super(R.id.locationsList);
        }

        public void loadData(){
            // TODO
        }
    }

    class TicketsTab extends Tab<Object> {
        public TicketsTab(){
            super(R.id.ticketsList);
        }

        public void loadData(){
            // TODO
        }
    }
}
