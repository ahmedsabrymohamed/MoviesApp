package com.example.mine.popularmovies;

import java.util.ArrayList;
import java.util.List;

/**
 * This class for the Response of the API Request for Images of Specific Movie
 */

public class MoviesResponse {

        private  int total_pages;
        private List<Movie> results;

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
