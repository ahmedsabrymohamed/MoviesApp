package com.example.mine.popularmovies;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class extends AsyncTask class to connect to the web and fetch the data of movies  using background thread
 */

class FetchMovies extends AsyncTask<URL,Void,ArrayList<Movie>> {

    private final GridAdapter gridAdapter;


    public FetchMovies(GridAdapter gridAdapter) {
        this.gridAdapter=gridAdapter;
    }

    @Override
    protected ArrayList<Movie> doInBackground(URL... urls) {

        String JSONData;
        try {

            JSONData=fetchJSON(urls[0]);
           // Log.d("ahmed", "parseJSON: "+JSONData);
            return parseJSON(JSONData);

        }
        catch (IOException | JSONException e) {

            e.printStackTrace();
        }
        return  null;
    }



    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
       gridAdapter.setMovies(movies);
    }

    private static String fetchJSON(URL url) throws IOException {

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {

            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);

            scanner.useDelimiter("\\A");


            if (scanner.hasNext()) {

                return scanner.next();

            }
            else {

                return null;
            }
        }
        finally {

            httpURLConnection.disconnect();
        }
    }

    private ArrayList<Movie>parseJSON(String JSONData) throws JSONException {


        ArrayList<Movie> movies=new ArrayList<>();

        String voteCount="vote_count";
        String moviesResults="results";
        String totalPages="total_pages";
        String voteAvg="vote_average";
        String id="id";
        String title="title";
        String video="video";
        String posterPath="poster_path";
        String originalTitle="original_title";
        String originalLanguage="original_language";
        String backDropPath="backdrop_path";
        String adult="adult";
        String overview="overview";
        String releaseDate="release_date";


        JSONObject jsonObject=new JSONObject(JSONData);

        gridAdapter.setLastPage(jsonObject.getInt(totalPages));

        JSONArray moviesArray=jsonObject.getJSONArray(moviesResults);

        int moviesNumber=moviesArray.length();
        JSONObject movieX;

            for(int i=0;i<moviesNumber;i++){

                Movie movie=new Movie();

                movieX=(JSONObject) moviesArray.get(i);

                movie.setId(movieX.getString(id));

                movie.setAdult(movieX.getBoolean(adult));

                movie.setOverview(movieX.getString(overview));

                movie.setBackDropPath(movieX.getString(backDropPath));

                movie.setOriginalTitle(movieX.getString(originalTitle));

                movie.setPosterPath(movieX.getString(posterPath));

                movie.setTitle(movieX.getString(title));

                movie.setReleaseDate(movieX.getString(releaseDate));

                movie.setVoteAvg(movieX.getDouble(voteAvg));

                movie.setVideo(movieX.getBoolean(video));

                movie.setVoteCount(movieX.getInt(voteCount));

                movie.setOriginalLanguage(movieX.getString(originalLanguage));

                movies.add(movie);



            }


        return movies;
    }

}
