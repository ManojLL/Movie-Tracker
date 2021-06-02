/** containing edittext fields for get new data from user to create movie*/

package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movietracker.db.MovieDB;

public class RegisterMovie extends AppCompatActivity {

    EditText title, year, director, cast, rating, review;
    MovieDB movieDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        title = (EditText) findViewById(R.id.etTitle);
        year = (EditText) findViewById(R.id.etYear);
        director = (EditText) findViewById(R.id.etDirector);
        cast = (EditText) findViewById(R.id.etCast);
        rating = (EditText) findViewById(R.id.etRating);
        review = (EditText) findViewById(R.id.etReview);

        movieDB = new MovieDB(this);
    }

    /** on update button click check year is greater than 1895 and rating is between 1-10 and every
     * fields are not empty. Then update database to new movie details */
    public void btnSaveClick(View view) {
        try {
            String movieTitle = title.getText().toString();
            int movieYear = Integer.parseInt(year.getText().toString());
            String movieDirector = director.getText().toString();
            String movieCast = cast.getText().toString();
            int movieRating = Integer.parseInt(rating.getText().toString());
            String movieReview = review.getText().toString();

            if (movieTitle.isEmpty() || movieDirector.isEmpty() || movieCast.isEmpty() || movieReview.isEmpty()) {
                Toast toast = Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT);
                toast.show();
            } else {

                boolean validYear = true;
                boolean validRating = true;

                if (!(movieYear > 1895) && !(movieRating >= 1 && movieRating <= 10)) {
                    validYear = false;
                    validRating = false;
                    Toast toast = Toast.makeText(this, "Please check year and rating again", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (!(movieYear > 1895)) {
                    validYear = false;
                    Toast toast = Toast.makeText(this, "Year should greater than 1895", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (!(movieRating >= 1 && movieRating <= 10)) {
                    validRating = false;
                    Toast toast = Toast.makeText(this, "Rating should between 1 and 10", Toast.LENGTH_SHORT);
                    toast.show();
                }

                if (validYear && validRating) {
                    movieTitle = movieTitle.substring(0, 1).toUpperCase() + movieTitle.substring(1).toLowerCase();
                    System.out.println(movieTitle);

                    Cursor cursor = movieDB.getMovieData();

                    String newMovie = (movieTitle + " " + movieYear).toLowerCase();
                    boolean isExists = false; //store if movie title + movie year is already exists
                    if (cursor.getCount() != 0) {
                        while (cursor.moveToNext()) {
                            String temp = (cursor.getString(1) + " " + cursor.getString(2)).toLowerCase();
                            if (newMovie.equals(temp)) {
                                isExists = true;
                                break;
                            }
                        }
                    }

                    if (isExists) {
                        Toast toast = Toast.makeText(this, "This movie is already exists", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {

                        boolean flag = movieDB.insertMovie(movieTitle, movieYear, movieDirector, movieCast, movieRating, movieReview);
                        if (flag) {
                            Toast toast = Toast.makeText(this, "New movie data inserted", Toast.LENGTH_SHORT);
                            toast.show();
                            title.setText("");
                            year.setText("");
                            director.setText("");
                            cast.setText("");
                            rating.setText("");
                            review.setText("");
                        } else {
                            Toast toast = Toast.makeText(this, "New movie data NOT inserted", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }

            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}