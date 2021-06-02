/** containing edittext fields for get new data from user to update movie*/

package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.movietracker.db.MovieDB;

public class EditMovieDetails extends AppCompatActivity {

    EditText title, year, director, cast, rating, review;
    RadioGroup radioGroup;
    RadioButton favourite, notFavourite;
    RatingBar ratingBar;
    MovieDB movieDB;
    Cursor cursor;
    String movieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie_details);

        title = (EditText) findViewById(R.id.etTitle);
        year = (EditText) findViewById(R.id.etYear);
        director = (EditText) findViewById(R.id.etDirector);
        cast = (EditText) findViewById(R.id.etCast);
        rating = (EditText) findViewById(R.id.etRating);
        review = (EditText) findViewById(R.id.etReview);
        radioGroup = (RadioGroup) findViewById(R.id.favStatus);
        favourite = (RadioButton) findViewById(R.id.favourite);
        notFavourite = (RadioButton) findViewById(R.id.notfavourite);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        Intent intent = getIntent();
        movieID = intent.getStringExtra("Movie_ID");

        movieDB = new MovieDB(this);
        cursor = movieDB.getMovieDetails(Integer.valueOf(movieID));

        //adding current data to displayed fields
        while (cursor.moveToNext()) {
            title.setText(cursor.getString(1));
            year.setText(cursor.getString(2));
            director.setText(cursor.getString(3));
            cast.setText(cursor.getString(4));
            ratingBar.setRating(Float.parseFloat(cursor.getString(5)));
            review.setText(cursor.getString(6));
            if (cursor.getString(7).equals("1")) {
                favourite.setChecked(true);
            } else {
                notFavourite.setChecked(true);
            }
        }

    }

    /** on update button click check year is greater than 1895 and rating is between 1-10 and every
     * fields are not empty. Then update database to new movie details */
    public void btnUpdateClick(View view) {
        String movieTitle = title.getText().toString();
        int movieYear = Integer.parseInt(year.getText().toString());
        String movieDirector = director.getText().toString();
        String movieCast = cast.getText().toString();
        int movieRating = (int) ratingBar.getRating();
        String movieReview = review.getText().toString();
        boolean fav;

        //set status for checkbox if movie is favourite
        if (favourite.isChecked()) {
            fav = true;
            System.out.println(fav);
        } else {
            fav = false;
            System.out.println(fav);
        }

        //check every fields are empty or not
        if (movieTitle.isEmpty() || movieDirector.isEmpty() || movieCast.isEmpty() || movieReview.isEmpty()) {
            Toast toast = Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            boolean validYear = true; //true if entered year is valid
            boolean validRating = true; //true if entered rating is valid


            if (!(movieYear > 1895)) {
                validYear = false;
                Toast toast = Toast.makeText(this, "Year should greater than 1895", Toast.LENGTH_SHORT);
                toast.show();
            } else if (!(movieRating >= 1 && movieRating <= 10)) {
                validRating = false;
                Toast toast = Toast.makeText(this, "Rating should between 1 and 10", Toast.LENGTH_SHORT);
                toast.show();
            }

            //update db if year and rating is valid
            if (validYear && validRating) {
                movieDB.update(Integer.valueOf(movieID), movieTitle, movieYear, movieDirector, movieCast, movieRating, movieReview, fav);
                Toast toast = Toast.makeText(this, "Movie details updated", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        }
    }
}