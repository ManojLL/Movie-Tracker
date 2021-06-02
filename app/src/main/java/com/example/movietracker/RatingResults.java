/** this class is for display IMDB rating results */

package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.movietracker.adapter.RatingResultAdapter;

import java.util.ArrayList;

public class RatingResults extends AppCompatActivity {

    ListView list;

    ArrayList<String> titles; //stores movie titles
    ArrayList<String> rating; //stores rating
    private static ArrayList<byte[]> images; //stores encoded image data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_results);

        Intent intent = getIntent();
        titles = intent.getStringArrayListExtra("Title");
        rating = intent.getStringArrayListExtra("Rating");

        list = (ListView) findViewById(R.id.imdblist);

        //calling adapter for display custom listview items
        RatingResultAdapter adapter = new RatingResultAdapter(this, titles, rating, images);
        list.setAdapter(adapter);

    }

    public static void setImages(ArrayList<byte[]> images) {
        RatingResults.images = images;
    }
}