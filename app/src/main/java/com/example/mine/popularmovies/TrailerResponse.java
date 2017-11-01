package com.example.mine.popularmovies;

import java.util.List;

/**
 * Created by mine on 01/11/17.
 */

public class TrailerResponse {

    private List<Trailer> results;

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
