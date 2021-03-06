package nl.bioscoop.mijnbios;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import java.util.ArrayList;

import nl.bioscoop.biosapi.BiosAPI;
import nl.bioscoop.biosapi.database.BiosDatabase;
import nl.bioscoop.biosapi.database.TicketDAO;
import nl.bioscoop.biosapi.model.Cinema;
import nl.bioscoop.biosapi.model.Movie;
import nl.bioscoop.biosapi.model.Ticket;
import nl.bioscoop.biosapi.utils.DataLoader;
import nl.bioscoop.mijnbios.adapters.CinemaAdapter;
import nl.bioscoop.mijnbios.adapters.MovieAdapter;
import nl.bioscoop.mijnbios.adapters.TicketsAdapter;

import static nl.bioscoop.mijnbios.utils.Async.async;

public class MainActivity extends AppCompatActivity {
    private BiosAPI api;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;
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

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int position = menuItem.getItemId() == R.id.movies ? 0 :
                            menuItem.getItemId() == R.id.locations ? 1 :
                            menuItem.getItemId() == R.id.tickets ? 2 : -1;
            switchTab(position, false);
            return position != -1;
        });

        Intent intent = getIntent();
        switchTab(intent.getIntExtra(Config.EXTRA_TABID, 0), true);
    }

    @Override protected void onNewIntent(Intent intent) {
        switchTab(intent.getIntExtra(Config.EXTRA_TABID, 0), true);
    }

    public void switchTab(int position, boolean updateBottomNavigation) {
        if (updateBottomNavigation){
            bottomNavigationView.setSelectedItemId(position == 0 ? R.id.movies :
                                                    position == 1 ? R.id.locations :
                                                    position == 2 ? R.id.tickets : -1);
        } else {
            tabs[0].onHide();
            tabs[1].onHide();
            tabs[2].onHide();
            tabs[position].onShow();
        }
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        AbsListView listView = tabs[0].listView;
        if(listView != null && listView instanceof GridView){
            GridView gridView = (GridView) listView;
            gridView.setNumColumns(getResources().getInteger(R.integer.numColumns));
        }
    }

    abstract class Tab<T> {
        protected ArrayList<T> list;
        protected AbsListView listView;
        private boolean hasDataBeenLoaded = false;

        public Tab(int listViewId){
            list = new ArrayList<>();

            listView = findViewById(listViewId);
        }

        public void onShow(){
            listView.setVisibility(View.VISIBLE);
            if(!hasDataBeenLoaded){
                hasDataBeenLoaded = true;
                loadData();
            }
        }

        abstract void loadData();

        public void onHide(){
            listView.setVisibility(View.GONE);
        }
    }

    class MoviesTab extends Tab<Movie> {
        public MoviesTab(){
            super(R.id.movieGrid);

            listView.setAdapter(new MovieAdapter(MainActivity.this, list));
            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                Movie movie = ((MovieAdapter) listView.getAdapter()).getItem(i);
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

    class LocationsTab extends Tab<Cinema> {
        public LocationsTab(){
            super(R.id.locationsList);
            listView.setAdapter(new CinemaAdapter(MainActivity.this, list));
        }

        public void loadData(){
            swipeRefreshLayout.setRefreshing(true);
            api.getCinemas((cinemaList) -> {
                list.clear();
                list.addAll(cinemaList);
                runOnUiThread(((CinemaAdapter) listView.getAdapter())::notifyDataSetChanged);
                swipeRefreshLayout.setRefreshing(false);
            });
        }
    }

    class TicketsTab extends Tab<Ticket> {
        public TicketsTab(){
            super(R.id.ticketsList);

            listView.setAdapter(new TicketsAdapter(MainActivity.this, list));
            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                Ticket ticket = ((TicketsAdapter) listView.getAdapter()).getItem(i);
                if(ticket != null) {
                    Intent intent = new Intent(MainActivity.this, TicketDetailActivity.class);
                    intent.putExtra(Config.EXTRA_TICKET, ticket);
                    startActivity(intent);
                }
            });
        }

        @Override @CallSuper public void onShow() {
            super.onShow();
            loadData();
        }

        public void loadData(){
            swipeRefreshLayout.setRefreshing(true);
            TicketDAO ticketDAO = BiosDatabase.getInstance(MainActivity.this).getDB().ticketDAO();
            async(ticketDAO::getTickets, (tickets) -> {
                list.clear();
                list.addAll(tickets);
                runOnUiThread(((TicketsAdapter) listView.getAdapter())::notifyDataSetChanged);
                swipeRefreshLayout.setRefreshing(false);
            });
        }
    }
}
