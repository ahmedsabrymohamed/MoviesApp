package com.example.mine.popularmovies;



import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to get the Response of the Images of  specific movie
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
