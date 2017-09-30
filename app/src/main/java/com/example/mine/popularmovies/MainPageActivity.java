package com.example.mine.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;

public class MainPageActivity extends AppCompatActivity implements GridAdapter.SetOncLickListener {

    private final static String SETTINGS_SELECTED = "selectedCategory";
    private SharedPreferences settings;
    private RecyclerView moviesGrid;
    private GridAdapter gridAdapter;
    private Integer pageNumber=1;
    private static final String API_KEY = BuildConfig.APIKey;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int moviesNumber=((int)dpWidth/101);
        setContentView(R.layout.activity_main_page);
        settings = getSharedPreferences(getString(R.string.settingData), MODE_PRIVATE);
        gridAdapter = new GridAdapter(this);
        moviesGrid = (RecyclerView) findViewById(R.id.moviesGrid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, moviesNumber);
        moviesGrid.setLayoutManager(gridLayoutManager);
        moviesGrid.setHasFixedSize(true);
        moviesGrid.setAdapter(gridAdapter);
        pageNumber=1;
        refresh(pageNumber);
        moviesGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!moviesGrid.canScrollVertically(1)){
                    if(pageNumber<=gridAdapter.getLastPage()) {

                        pageNumber++;
                        refresh(pageNumber);
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        String movieCategory;
        switch (id){

            case R.id.top_rated:
                movieCategory="top_rated";
                break;
            case R.id.now_playing:
                movieCategory="now_playing";
                break;
            case R.id.upcoming:
                movieCategory="upcoming";
                break;
            default:
                movieCategory="popular";
        }

        settings.edit().putString(SETTINGS_SELECTED, movieCategory).apply();

        gridAdapter.refresh();
        refresh(1);


        return true;
    }

    private void refresh(Integer pageNumber) {



        String moviesCategory = settings.getString(SETTINGS_SELECTED, "popular");

        URL url = Network.buildURL(API_KEY, moviesCategory, pageNumber);

        FetchMovies fetchMovies = new FetchMovies(gridAdapter);

        fetchMovies.execute(url);

    }

    @Override
    public void SetOnclick(Movie movie) {

        Intent intent =new Intent(this,DetailActivity.class);
        Bundle bundle =new Bundle();
        bundle.putParcelable("Movie",movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}