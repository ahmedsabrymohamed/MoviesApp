package com.example.mine.popularmovies;

import java.util.List;

/**
 * This class is used to get the Response of the Trailers of  specific movie
 */

class TrailerResponse {

    private List<Trailer> results;

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
