package com.example.mine.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;

import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.RatingBar;

import android.widget.TextView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class DetailActivity extends AppCompatActivity {

    private Movie movie=null;
    private final static String BaseURL="https://image.tmdb.org/t/p/w500";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation=getResources().getConfiguration().orientation;

        if(orientation== Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_detail_landscap);
        else
            setContentView(R.layout.activity_detail);


        Intent intent=getIntent();
        if(intent!=null){
            Bundle bundle=intent.getExtras();
            if(bundle!=null){

                movie=bundle.getParcelable("Movie");
            }
        }



        SimpleDraweeView moviePoster = (SimpleDraweeView) findViewById(R.id.movieDraweeView);

        RatingBar movieRate = (RatingBar) findViewById(R.id.ratingBar);
        movieRate.setNumStars(5);
        movieRate.setRating((float)(movie.getVoteAvg()/2.0));

        TextView movieOriginalTitle = (TextView) findViewById(R.id.movieOriginalTitle);
        movieOriginalTitle.setText(movie.getOriginalTitle());

        TextView movieOriginalLanguage = (TextView) findViewById(R.id.movieOriginalLanguage);
        movieOriginalLanguage.setText(getString(R.string.language)+movie.getOriginalLanguage());

        TextView movieOverview = (TextView) findViewById(R.id.movieOverview);
        movieOverview.setText(movie.getOverview());

        TextView movieReleaseDate = (TextView) findViewById(R.id.movieReleaseDate);
        movieReleaseDate.setText(getString(R.string.releaseDate)+movie.getReleaseDate());

        TextView adult = movieOriginalTitle = (TextView) findViewById(R.id.movieType);
        adult.setText((movie.isAdult()?getString(R.string.adult):getString(R.string.family)));

        TextView voteCount = (TextView) findViewById(R.id.movieVoteCount);
        voteCount.setText(getString(R.string.voteCount)+movie.getVoteCount());

        String poster=movie.getPosterPath();

        if(orientation== Configuration.ORIENTATION_LANDSCAPE)
            poster=movie.getBackDropPath();

        Uri uri=Uri.parse(BaseURL+poster);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(800, 800))
                .build();
        moviePoster.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(moviePoster.getController())
                        .setImageRequest(request)
                        .build());


    }
}
