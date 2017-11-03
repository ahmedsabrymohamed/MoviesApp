package com.example.mine.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * class to store the movies data fetched from the web
 */

class Movie implements Parcelable{

    private boolean video;
    private String id;
    private double vote_average;
    private String title;
    private String original_title;
    private String original_language;
    private String poster_path;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private int vote_count;
    private boolean favorite;


    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Movie() {

    }

    private Movie(Parcel in) {

        video=in.readByte()!=0;
        id=in.readString();
        vote_average =in.readDouble();
        title=in.readString();
        original_title =in.readString();
        original_language =in.readString();
        poster_path =in.readString();
        backdrop_path =in.readString();
        adult=in.readByte()!=0;
        overview=in.readString();
        release_date =in.readString();
        vote_count =in.readInt();
        favorite=in.readByte()!=0;




    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeByte((video)?(byte)1:(byte)0);
        parcel.writeString(id);
        parcel.writeDouble(vote_average);
        parcel.writeString(title);
        parcel.writeString(original_title);
        parcel.writeString(original_language);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeByte((adult)?(byte)1:(byte)0);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeInt(vote_count);
        parcel.writeByte((favorite)?(byte)1:(byte)0);

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        public Movie createFromParcel(Parcel in)
        {
            return new Movie(in);
        }
        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_avg) {
        this.vote_average = vote_avg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getVote_count() {return vote_count;}

    public void setVote_count(int vote_count) {this.vote_count = vote_count;}


}
