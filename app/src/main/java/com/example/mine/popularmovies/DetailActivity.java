package com.example.mine.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;

;
import android.net.Uri;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import android.widget.TextView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements ListAdapter.SetOncLickListener{

    private Movie movie=null;
    private ListAdapter reviewsAdapter;
    private ListAdapter trailersAdapter;

    private final static String BaseURL="https://image.tmdb.org/t/p/w500";
    private   List<MovieImage> images ;
    private  SimpleDraweeView moviePoster;
    private final static String SHOW_TYPE="movie";
    private static final String API_KEY = BuildConfig.APIKey;
    private final  Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation=getResources().getConfiguration().orientation;

        if(orientation== Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_detail_landscap);
        else
            setContentView(R.layout.activity_detail);


        movie=new Movie();
        Intent intent=getIntent();
        if(intent!=null){
            Bundle bundle=intent.getExtras();
            if(bundle!=null){

                movie=bundle.getParcelable("Movie");

            }
        }

        images= new ArrayList<>();
        fetchImages(images);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        moviePoster = (SimpleDraweeView) findViewById(R.id.movieDraweeView);

        TextView movieRate = (TextView) findViewById(R.id.ratingBar);
        movieRate.setText(Double.toString(movie.getVote_average()));

        TextView movieOriginalTitle = (TextView) findViewById(R.id.movieOriginalTitle);
        movieOriginalTitle.setText(movie.getOriginal_title());

        TextView movieOriginalLanguage = (TextView) findViewById(R.id.movieOriginalLanguage);
        movieOriginalLanguage.setText(getString(R.string.language)+movie.getOriginal_language());

        TextView movieOverview = (TextView) findViewById(R.id.movieOverview);
        movieOverview.setText(movie.getOverview());

        TextView movieReleaseDate = (TextView) findViewById(R.id.movieReleaseDate);
        movieReleaseDate.setText(getString(R.string.release_date)+movie.getRelease_date());

        TextView adult =(TextView) findViewById(R.id.movieType);
        adult.setText((movie.isAdult()?getString(R.string.adult):getString(R.string.family)));

        TextView voteCount = (TextView) findViewById(R.id.movieVoteCount);
        voteCount.setText(getString(R.string.vote_count)+movie.getVote_count());

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)moviePoster.getLayoutParams();





        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height =(int)(dpHeight*0.55);

        if(orientation== Configuration.ORIENTATION_LANDSCAPE)
        {

            params.width = (int)(dpWidth*0.35);
            params.height =LinearLayout.LayoutParams.MATCH_PARENT;
        }


        moviePoster.setLayoutParams(params);


        RecyclerView trailersList=(RecyclerView) findViewById(R.id.trailers_list);
        RecyclerView reviewsList=(RecyclerView) findViewById(R.id.reviews_list);

        LinearLayoutManager trailersLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this);

        trailersList.setLayoutManager(trailersLayoutManager);
        trailersList.setHasFixedSize(true);

        reviewsList.setLayoutManager(reviewsLayoutManager);
        reviewsList.setHasFixedSize(true);

        trailersAdapter=new ListAdapter("trailer");
        trailersAdapter.setTrailerItemOncLickListener(this);

        reviewsAdapter=new ListAdapter("review");

        trailersList.setAdapter(trailersAdapter);
        reviewsList.setAdapter(reviewsAdapter);

        fetchReviews();
        fetchTrailers();



    }



    private void fetchImages(final List<MovieImage> images){

        ApiInterface apiService =
                APIClient.getClient().create(ApiInterface.class);

        final String TAG=this.getClass().getSimpleName();
        Call<ImagesResponse> call =
                apiService.getMovieDetails(SHOW_TYPE,
                        movie.getId()
                        , API_KEY
                        ,movie.getOriginal_language()
                        ,"en,null");
        call.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse>call, Response<ImagesResponse> response) {


                images.addAll(response.body().getBackdrops());
                images.addAll(response.body().getPosters());
                final int imagesNumber=images.size();
                startTimer(imagesNumber);


            }

            @Override
            public void onFailure(Call<ImagesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });




    }


    private void startTimer(final int imagesNumber){


        changePoster(imagesNumber);

        new CountDownTimer(5000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {

                changePoster(imagesNumber);
                start();
            }
        }.start();
    }



    private void changePoster(int imagesNumber){



        int index=rand.nextInt(imagesNumber);
        Uri uri=Uri.parse(BaseURL+images.get(index ).getFile_path());

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(images.get(index ).getWidth(),images.get(index ).getHeight()))
                .build();
        moviePoster.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(moviePoster.getController())
                        .setImageRequest(request)
                        .build());
    }


    private void fetchTrailers(){

        ApiInterface apiService =
                APIClient.getClient().create(ApiInterface.class);

        final String TAG=this.getClass().getSimpleName();

        Call<TrailerResponse> call =
                apiService.getMovieVideos(SHOW_TYPE,
                        movie.getId()
                        , API_KEY);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse>call, Response<TrailerResponse> response) {


                trailersAdapter.setTrailers(response.body().getResults());



            }

            @Override
            public void onFailure(Call<TrailerResponse>call, Throwable t) {
                // Log error here since request failed
                 Log.e(TAG, t.toString());
            }
        });

    }
    private void fetchReviews(){

        final String TAG=this.getClass().getSimpleName();
        final ApiInterface apiService =
                APIClient.getClient().create(ApiInterface.class);


        Call<ReviewsResponse> call =
                apiService.getMovieReviews(SHOW_TYPE,
                        movie.getId()
                        , API_KEY);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse>call, Response<ReviewsResponse> response) {


                reviewsAdapter.setReviews(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ReviewsResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    @Override
    public void SetOnclick(Trailer trailer) {


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

    }
}
