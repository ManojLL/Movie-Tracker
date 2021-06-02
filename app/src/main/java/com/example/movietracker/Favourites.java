/** this class is containing all favourite movies */

package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movietracker.adapter.FavouritesAdapter;
import com.example.movietracker.db.MovieDB;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    ListView list;
    MovieDB movieDB;
    ArrayList<String> movies; //to store fav movie titles for display in listview
    ArrayList<String> favouriteMovies; //to store current fav movies

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        list = (ListView) findViewById(R.id.list2);

        movieDB = new MovieDB(this);

        Cursor cursor = movieDB.getMovieData();

        if (cursor.getCount() == 0) {
            Toast toast = Toast.makeText(this, "No entry exists", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            movies = new ArrayList<>();
            favouriteMovies = new ArrayList<>();

            while (cursor.moveToNext()) {
                String title = cursor.getString(1);

                if (cursor.getString(7).equals("1")) {
                    movies.add(title);
                    favouriteMovies.add(title);
                }
            }

            //if user havent favourite movies displays this
            if (favouriteMovies.isEmpty()) {
                Toast toast = Toast.makeText(this, "You have no favourite movies", Toast.LENGTH_SHORT);
                toast.show();
            }

            //calling adapter class for display custom listview
            FavouritesAdapter adapter = new FavouritesAdapter(this, movies, favouriteMovies);
            list.setAdapter(adapter);

        }
    }

    /** on save button click update db favourite colomn */
    public void btnSaveClick(View view) {
        Cursor cursor = movieDB.getMovieData();
        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);

            boolean flag = false;
            for (String s : favouriteMovies) {
                if (s.equals(title)) {
                    movieDB.setAsFavourite(id, true);
                    flag = true;
                    break;
                }
            }

            //if not favourite then update fav colomn of db to false
            if (!flag) {
                movieDB.setAsFavourite(id, false);
            }
        }

        Toast toast = Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT);
        toast.show();
    }
}