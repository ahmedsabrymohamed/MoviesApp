package com.example.mine.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPageActivity extends AppCompatActivity implements  GridAdapter.SetOncLickListener
        ,LoaderManager.LoaderCallbacks<Cursor>  {


    private final static String SETTINGS_SELECTED = "selectedCategory";
    private SharedPreferences settings;
    private RecyclerView moviesGrid;
    private GridAdapter gridAdapter;
    private Integer pageNumber=1;
    private final static String SHOW_TYPE="movie";
    private static final String API_KEY = BuildConfig.APIKey;
    private static final int OFFLINE_LOADER=1001;
    private Toast networkInfo;
    private MenuItem topRated;
    private MenuItem mostPopular;
    private MenuItem upComing;
    private MenuItem nowPlaying;
    private MenuItem favorite;
    private boolean topRatedVal=false;
    private boolean mostPopularVal=false;
    private boolean favoriteVal=false;
    private boolean upComingVal=false;
    private boolean nowPlayingVal=false;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int moviesNumber=((int)dpWidth/101);
        setContentView(R.layout.activity_main_page);
        settings = getSharedPreferences(getString(R.string.settingData), MODE_PRIVATE);

        gridAdapter = new GridAdapter(this);
        moviesGrid = (RecyclerView) findViewById(R.id.moviesGrid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,moviesNumber);
        moviesGrid.setLayoutManager(gridLayoutManager);
        moviesGrid.setHasFixedSize(true);
        moviesGrid.setAdapter(gridAdapter);
        networkInfo=null;

        pageNumber=1;
        List<Movie> moviesData ;
        if(savedInstanceState!=null) {
            moviesData = savedInstanceState.getParcelableArrayList("moviesData");

        }
        else{
            moviesData=null;
        }
        if (moviesData == null) {
            refresh(pageNumber);
            Log.v("ahmed", "worked");
        } else {
            gridAdapter.setMovies(moviesData);
        }



        moviesGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!moviesGrid.canScrollVertically(1)){
                    if(pageNumber<=gridAdapter.getLastPage()) {

                        ConnectivityManager connectivityManager
                                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                        if(networkInfo!=null&&networkInfo.isConnected()){
                            pageNumber++;
                            refresh(pageNumber);

                        }
                        else{
                           notifyUser();

                        }


                    }
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.category_menu, menu);
        mostPopular=menu.getItem(0);
        topRated=menu.getItem(1);
        upComing=menu.getItem(2);
        nowPlaying=menu.getItem(3);
        favorite=menu.getItem(4);
        topRated.setChecked(topRatedVal);
        mostPopular.setChecked(mostPopularVal);
        favorite.setChecked(favoriteVal);
        upComing.setChecked(upComingVal);
        nowPlaying.setChecked(nowPlayingVal);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        item.setChecked(true);
        String movieCategory;
        switch (id){

            case R.id.top_rated:
                movieCategory="top_rated";
                topRated.setChecked(true);
                mostPopular.setChecked(false);
                favorite.setChecked(false);
                upComing.setChecked(false);
                nowPlaying.setChecked(false);
                break;
            case R.id.now_playing:
                movieCategory="now_playing";
                topRated.setChecked(false);
                mostPopular.setChecked(false);
                favorite.setChecked(false);
                upComing.setChecked(false);
                nowPlaying.setChecked(true);
                break;
            case R.id.upcoming:
                movieCategory="upcoming";
                topRated.setChecked(false);
                mostPopular.setChecked(false);
                favorite.setChecked(false);
                upComing.setChecked(true);
                nowPlaying.setChecked(false);
                break;
            case R.id.favorite:
                movieCategory="favorite";
                topRated.setChecked(false);
                mostPopular.setChecked(false);
                favorite.setChecked(true);
                upComing.setChecked(false);
                nowPlaying.setChecked(false);
                break;
            default:
                movieCategory="popular";
                topRated.setChecked(false);
                mostPopular.setChecked(true);
                favorite.setChecked(false);
                upComing.setChecked(false);
                nowPlaying.setChecked(false);
        }

        settings.edit().putString(SETTINGS_SELECTED, movieCategory).apply();


        gridAdapter.refresh();
        refresh(1);


        return true;
    }

    private void refresh(final Integer pageNumber){



        final String TAG=this.getClass().getSimpleName();
        String moviesCategory = settings.getString(SETTINGS_SELECTED, "popular");


        if(moviesCategory.equals("favorite")){


            getSupportLoaderManager().restartLoader(OFFLINE_LOADER, null, this);


        }
        else {
            ApiInterface apiService =
                    APIClient.getClient().create(ApiInterface.class);

            Call<MoviesResponse> call = apiService.getMoviesData(SHOW_TYPE, moviesCategory, API_KEY, pageNumber.toString());
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();

                    gridAdapter.setLastPage(response.body().getTotal_pages());
                    gridAdapter.setMovies(movies);



                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }



    }

    @Override
    public void SetOnclick(Movie movie) {



        movie.setFavorite(new DatabaseHelper(this).findMovie(movie.getId()));
        final Intent intent =new Intent(this,DetailActivity.class);



        Bundle bundle =new Bundle();
        bundle.putParcelable("Movie",movie);
        intent.putExtras(bundle);
        startActivity(intent);

    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            @Override
            protected void onStopLoading() {
                cancelLoad();
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                forceLoad();
            }

            @Override
            public Cursor loadInBackground() {

                String moviesCategory = settings.getString(SETTINGS_SELECTED, "popular");

                if (moviesCategory.equals("favorite")) {

                    try {

                        return getContentResolver().query(DatabaseContract.Movies.ALL_MOVIES_URI
                                , null
                                , null
                                , null
                                , null
                                , null);
                    } catch (Exception e) {
                        return null;
                    }
                }
                return null;
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(data!=null) {
            List<Movie> movies = new ArrayList<>();
            data.moveToFirst();
            while (!data.isAfterLast()) {
                Movie movie = new Movie();

                movie.setId(data.getString(data.getColumnIndex(DatabaseContract.Movies.ID)));

                movie.setAdult(data.getInt(data.getColumnIndex(DatabaseContract.Movies.ADULT)) == 1);

                movie.setRelease_date(data.getString(data.getColumnIndex(DatabaseContract.Movies.RELEASE_DATE)));

                movie.setOverview(data.getString(data.getColumnIndex(DatabaseContract.Movies.OVERVIEW)));

                movie.setBackdrop_path(data.getString(data.getColumnIndex(DatabaseContract.Movies.BACKDROP_PATH)));

                movie.setPoster_path(data.getString(data.getColumnIndex(DatabaseContract.Movies.POSTER_PATH)));

                movie.setTitle(data.getString(data.getColumnIndex(DatabaseContract.Movies.TITLE)));

                movie.setOriginal_title(data.getString(data.getColumnIndex(DatabaseContract.Movies.ORIGINAL_TITLE)));

                movie.setOriginal_language(data.getString(data.getColumnIndex(DatabaseContract.Movies.ORIGINAL_LANGUAGE)));

                movie.setVideo(data.getInt(data.getColumnIndex(DatabaseContract.Movies.VIDEO)) == 1);

                movie.setVote_average(data.getDouble(data.getColumnIndex(DatabaseContract.Movies.VOTE_AVERAGE)));

                movie.setVote_count(data.getInt(data.getColumnIndex(DatabaseContract.Movies.VOTE_COUNT)));

                movie.setFavorite(data.getInt(data.getColumnIndex(DatabaseContract.Movies.FAVORITE)) == 1);

                movies.add(movie);

                data.moveToNext();

            }


            gridAdapter.refresh();
            gridAdapter.setLastPage(data.getCount() / 19);
            gridAdapter.setMovies(movies);
        }



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void notifyUser(){
        if(networkInfo!=null)
            networkInfo.cancel();
        networkInfo=Toast.makeText(getApplication(),"There is No INTERNET Connection",Toast.LENGTH_LONG);
        networkInfo.show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("topRated",topRated.isChecked());
        outState.putBoolean("mostPopular", mostPopular.isChecked());
        outState.putBoolean("favorite",favorite.isChecked());
        outState.putBoolean("upComing",upComing.isChecked());
        outState.putBoolean("nowPlaying",nowPlaying.isChecked());
        outState.putParcelableArrayList("moviesData", gridAdapter.getMovies());
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        topRatedVal=savedInstanceState.getBoolean("topRated");
        mostPopularVal=savedInstanceState.getBoolean("mostPopular");
        favoriteVal=savedInstanceState.getBoolean("favorite");
        upComingVal=savedInstanceState.getBoolean("upComing");
        nowPlayingVal=savedInstanceState.getBoolean("nowPlaying");
        super.onRestoreInstanceState(savedInstanceState);
    }

}