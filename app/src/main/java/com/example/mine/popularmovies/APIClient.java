package com.example.mine.popularmovies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  initialize Retrofit Library
 */

class APIClient {

    private final static String BASE_URL="https://api.themoviedb.org/3/";
    private static Retrofit retrofit=null;
    public  static Retrofit getClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
