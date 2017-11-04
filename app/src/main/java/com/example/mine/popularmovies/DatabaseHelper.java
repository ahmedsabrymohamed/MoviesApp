package com.example.mine.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * This class inherit from SQLiteOpenHelper class to be used to manipulate the TMDB.db
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="TMDB.db";
    public static final int DATABASE_VERSION=3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createMovies="CREATE TABLE "+DatabaseContract.Movies.TABLE_NAME+
                " ( "+DatabaseContract.Movies._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DatabaseContract.Movies.TITLE+" TEXT ,"
                +DatabaseContract.Movies.ORIGINAL_TITLE+" TEXT,"
                +DatabaseContract.Movies.ORIGINAL_LANGUAGE+" TEXT,"
                +DatabaseContract.Movies.OVERVIEW+" TEXT,"
                +DatabaseContract.Movies.RELEASE_DATE+" TEXT,"
                +DatabaseContract.Movies.BACKDROP_PATH+" TEXT,"
                +DatabaseContract.Movies.POSTER_PATH+" TEXT,"
                +DatabaseContract.Movies.ID+" TEXT NOT NULL,"
                +DatabaseContract.Movies.VOTE_AVERAGE+" Double,"
                +DatabaseContract.Movies.VOTE_COUNT+" INTEGER,"
                +DatabaseContract.Movies.ADULT+" INTEGER,"
                +DatabaseContract.Movies.VIDEO+" INTEGER,"
                +DatabaseContract.Movies.FAVORITE+" INTEGER"+" );";


        sqLiteDatabase.execSQL(createMovies);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.Movies.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }
    public  boolean findMovie(String movieId){

        String findMovie="Select * From "+DatabaseContract.Movies.TABLE_NAME
                +" where "+DatabaseContract.Movies.ID+" = "+movieId+" ;";
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(findMovie,null);
        boolean exist=(cursor.getCount()>0);
        cursor.close();;
        return exist;

    }


}
