package com.example.mine.popularmovies;

import java.util.List;

/**
 * Created by mine on 01/11/17.
 */

public class ReviewsResponse {
   private List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
