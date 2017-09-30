package com.example.mine.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * class to store the movies data fetched from the web
 */

class Movie implements Parcelable{

    private boolean video;
    private String id;
    private double voteAvg;
    private String title;
    private String originalTitle;
    private String originalLanguage;
    private String posterPath;
    private String backDropPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private int voteCount;

    public Movie() {
    }

    private Movie(Parcel in) {

        video=in.readByte()!=0;
        id=in.readString();
        voteAvg=in.readDouble();
        title=in.readString();
        originalTitle=in.readString();
        originalLanguage=in.readString();
        posterPath=in.readString();
        backDropPath=in.readString();
        adult=in.readByte()!=0;
        overview=in.readString();
        releaseDate=in.readString();
        voteCount=in.readInt();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeByte((video)?(byte)1:(byte)0);
        parcel.writeString(id);
        parcel.writeDouble(voteAvg);
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        parcel.writeString(originalLanguage);
        parcel.writeString(posterPath);
        parcel.writeString(backDropPath);
        parcel.writeByte((adult)?(byte)1:(byte)0);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeInt(voteCount);

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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
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

    public double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(double vote_avg) {
        this.voteAvg = vote_avg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {return voteCount;}

    public void setVoteCount(int voteCount) {this.voteCount = voteCount;}


}
