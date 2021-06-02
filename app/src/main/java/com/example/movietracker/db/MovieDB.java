package com.example.movietracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class MovieDB extends SQLiteOpenHelper {

    public MovieDB(Context context) {
        super(context, "MovieData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table MovieDetails(" + _ID + " INTEGER primary key autoincrement, title TEXT, year INTEGER, director TEXT, actors TEXT, rating INTEGER, review TEXT, isFavourite BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists MovieDetails");
    }

    //add new movie data
    public boolean insertMovie(String title, int year, String director, String actors, int rating, String review) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("year", year);
        contentValues.put("director", director);
        contentValues.put("actors", actors);
        contentValues.put("rating", rating);
        contentValues.put("review", review);
        contentValues.put("isFavourite", false); //initially favourite is set as false
        long result = DB.insert("MovieDetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //return all data in db
    public Cursor getMovieData() {
        SQLiteDatabase DB = getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from MovieDetails order by title asc", null);
        return cursor;
    }

    //make movie as favourite
    public void setAsFavourite(int id, boolean value) {
        SQLiteDatabase DB = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isFavourite", value);
        DB.update("MovieDetails", contentValues, _ID + "=?", new String[]{String.valueOf(id)});
    }

    //return data in db for specific movie
    public Cursor getMovieDetails(int id) {
        SQLiteDatabase DB = getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from MovieDetails where " + _ID + " = " + id, null);
        return cursor;
    }

    //update movie data
    public void update(int id, String title, int year, String director, String actors, int rating, String review, boolean fav) {
        SQLiteDatabase DB = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("year", year);
        contentValues.put("director", director);
        contentValues.put("actors", actors);
        contentValues.put("rating", rating);
        contentValues.put("review", review);
        contentValues.put("isFavourite", fav);
        DB.update("MovieDetails", contentValues, _ID + "=?", new String[]{String.valueOf(id)});
    }
}
