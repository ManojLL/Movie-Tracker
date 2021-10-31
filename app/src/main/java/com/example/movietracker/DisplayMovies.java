/**
 * this class is for display all movies
 */
package com.example.movietracker;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movietracker.adapter.DisplayMoviesAdapter;
import com.example.movietracker.db.MovieDB;

import java.util.ArrayList;

public class DisplayMovies extends AppCompatActivity {

    ListView list;
    MovieDB movieDB;
    ArrayList<String> favouriteMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        list = (ListView) findViewById(R.id.list1);

        movieDB = new MovieDB(this);

        Cursor cursor = movieDB.getMovieData();

        //if movie database is empty it displays the toast massage
        if (cursor.getCount() == 0) {
            Toast toast = Toast.makeText(this, "No entry exists", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String[] movieTitle = new String[cursor.getCount()];
            favouriteMovies = new ArrayList<String>();
            int count = 0;

            while (cursor.moveToNext()) {
                movieTitle[count] = cursor.getString(1);
                count++;
            }

            //calling custom listview adapter to display all movies
            DisplayMoviesAdapter adapter = new DisplayMoviesAdapter(this, movieTitle, favouriteMovies);
            list.setAdapter(adapter);
        }
    }

    //on add to favourite btn click update the database
    public void btnAddToFavClick(View view) {
        Cursor cursor = movieDB.getMovieData();
        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);

            for (String s : favouriteMovies) {
                if (s.equals(title)) {
                    movieDB.setAsFavourite(id, true);
                }
            }
        }

        Toast toast = Toast.makeText(this, "Added to favourites successfully", Toast.LENGTH_SHORT);
        toast.show();
    }
}