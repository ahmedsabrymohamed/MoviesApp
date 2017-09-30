package com.example.mine.popularmovies;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * this is used to build the URL
 */

class Network {

    private final static String BASE_URL="https://api.themoviedb.org/3";
    private final static String SHOW_TYPE="movie";
    private final static String LANGUAGE="en-US";
    private final static String QUERY_API="api_key";
    private final static String QUERY_LANGUAGE="language";
    private final static String QUERY_PAGE="page";

    public static URL buildURL(String APIKey,String moviesCategory,Integer pageNumber){

        Uri uri=Uri.parse(BASE_URL).buildUpon()
                .appendPath(SHOW_TYPE)
                .appendPath(moviesCategory)
                .appendQueryParameter(QUERY_API,APIKey)
                .appendQueryParameter(QUERY_LANGUAGE,LANGUAGE)
                .appendQueryParameter(QUERY_PAGE,pageNumber.toString()).build();


        URL url=null;

        try {

            url=new URL(uri.toString());
        }
        catch (MalformedURLException e) {

            e.printStackTrace();
        }
        return url;
    }

}
