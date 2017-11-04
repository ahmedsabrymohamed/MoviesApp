package com.example.mine.popularmovies;

import java.util.List;

/**
 * This class is used to get the Response of the Reviews of  specific movie
 */

class ReviewsResponse {
   private List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
