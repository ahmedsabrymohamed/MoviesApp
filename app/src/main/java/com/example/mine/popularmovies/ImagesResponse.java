package com.example.mine.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mine on 30/10/17.
 */

public class ImagesResponse {


        private List<MovieImage> backdrops;
        private List<MovieImage> posters;

    public ImagesResponse() {
        backdrops=new ArrayList<>();
        posters=new ArrayList<>();
    }

    public List<MovieImage> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<MovieImage> backdrops) {
        this.backdrops = backdrops;
    }

    public List<MovieImage> getPosters() {
        return posters;
    }

    public void setPosters(List<MovieImage> posters) {
        this.posters = posters;
    }





}
