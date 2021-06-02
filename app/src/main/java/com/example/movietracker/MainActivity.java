/** this class is containing main 6 buttons and their action events*/

package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterMovie.class);
        startActivity(intent);
    }

    public void btnDisplayMoviesClick(View view) {
        Intent intent = new Intent(this, DisplayMovies.class);
        startActivity(intent);
    }

    public void btnFavouritesClick(View view) {
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }

    public void btnEditMoviesClick(View view) {
        Intent intent = new Intent(this, EditMovie.class);
        startActivity(intent);
    }

    public void btnSearchClick(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void btnRatingsClick(View view) {
        Intent intent = new Intent(this, Ratings.class);
        startActivity(intent);
    }
}