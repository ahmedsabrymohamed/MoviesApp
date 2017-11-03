package com.example.mine.popularmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The class of the Content Provider
 */

public class MovieProvider extends ContentProvider {


    private DatabaseHelper dbHelper;
    public static final int ALL_MOVIES=100;
    public static final int MOVIE=101;

    private static final UriMatcher mUriMATCHER=buildUriMatcher();
    public static UriMatcher buildUriMatcher(){

        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.ALL_MOVIES_PATH,ALL_MOVIES);
        uriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.MOVIE_PATH,MOVIE);
        return  uriMatcher;

    }
    @Override
    public boolean onCreate() {

        Context context=getContext();
        dbHelper=new DatabaseHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {


        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int match=mUriMATCHER.match(uri);

        switch (match){
            case ALL_MOVIES:
                return db.query(DatabaseContract.Movies.TABLE_NAME
                        ,null
                        ,null
                        ,null
                        ,null
                        ,null
                        ,null);
            default:
                throw new UnsupportedOperationException("Unkown Uri"+uri);

        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int match=mUriMATCHER.match(uri);
        long t1;
        switch (match){
            case MOVIE:
               t1=db.insert(DatabaseContract.Movies.TABLE_NAME,null,contentValues);
                break;
            default:
                throw new UnsupportedOperationException("Unkown Uri"+uri);

        }
        if(t1<0){
            throw new android.database.SQLException("Failed TO SAVE TO Database ");
        }
        else{
            return ContentUris.withAppendedId(DatabaseContract.BASE_URI,t1);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int match=mUriMATCHER.match(uri);
        switch (match){
            case MOVIE:
                return db.delete(DatabaseContract.Movies.TABLE_NAME,s,strings);
            default:
                throw new UnsupportedOperationException("Unkown Uri"+uri);

        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }




}
