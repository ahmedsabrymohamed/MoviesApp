package com.example.mine.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class for the Image Data
 */

public class MovieImage  {

    private double aspect_ratio;
    private String file_path;
    private int height;
    private int width;



    public double getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(double aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
