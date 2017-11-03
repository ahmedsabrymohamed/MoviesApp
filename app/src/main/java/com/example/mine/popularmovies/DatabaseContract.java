package com.example.mine.popularmovies;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The Contract of the SQLite Database
 */

public class DatabaseContract {


    public static final String AUTHORITY = "com.example.mine.popularmovies";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String ALL_MOVIES_PATH = "Movies";
    public static final String MOVIE_PATH = "Movie";



    public static final class Movies implements BaseColumns {

        public static final Uri ALL_MOVIES_URI = BASE_URI.buildUpon().appendPath(ALL_MOVIES_PATH).build();
        public static final Uri MOVIE_URI = BASE_URI.buildUpon().appendPath(MOVIE_PATH).build();


        public static final String TABLE_NAME = "MOVIES";
        public static final String VIDEO = "VIDEO";
        public static final String  FAVORITE = "FAVORITE";
        public static final String ID = "ID";
        public static final String VOTE_AVERAGE = "VOTE_AVERAGE";
        public static final String VOTE_COUNT = "VOTE_COUNT";
        public static final String OVERVIEW = "OVERVIEW";
        public static final String TITLE = "TITLE";
        public static final String ORIGINAL_TITLE = "ORIGINAL_TITLE";
        public static final String ORIGINAL_LANGUAGE = "ORIGINAL_LANGUAGE";
        public static final String POSTER_PATH = "POSTER_PATH";
        public static final String BACKDROP_PATH = "BACKDROP_PATH";
        public static final String RELEASE_DATE = "RELEASE_DATE";
        public static final String ADULT = "ADULT";

    }
    public static ContentValues makeMovieContentValues(Movie movie){

        ContentValues movieValues=new ContentValues();
        movieValues.put(Movies.ID,movie.getId());
        movieValues.put(Movies.ADULT,(movie.isAdult()?1:0));
        movieValues.put(Movies.VIDEO,(movie.isVideo()?1:0));
        movieValues.put(Movies.BACKDROP_PATH,movie.getBackdrop_path());
        movieValues.put(Movies.ORIGINAL_LANGUAGE,movie.getOriginal_language());
        movieValues.put(Movies.ORIGINAL_TITLE,movie.getOriginal_title());
        movieValues.put(Movies.POSTER_PATH,movie.getPoster_path());
        movieValues.put(Movies.TITLE,movie.getTitle());
        movieValues.put(Movies.OVERVIEW,movie.getOverview());
        movieValues.put(Movies.VOTE_AVERAGE,movie.getVote_average());
        movieValues.put(Movies.RELEASE_DATE,movie.getRelease_date());
        movieValues.put(Movies.VOTE_COUNT,movie.getVote_count());
        movieValues.put(Movies.FAVORITE,(movie.isFavorite()?1:0));
        return movieValues;

    }
}
