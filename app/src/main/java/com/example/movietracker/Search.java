/** this class is for search movie based on title, director or cast */

package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movietracker.adapter.SearchResultAdapter;
import com.example.movietracker.db.MovieDB;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText user_input;
    ListView list;

    MovieDB movieDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        user_input = (EditText) findViewById(R.id.etTitle);
        list = (ListView) findViewById(R.id.list1);

        movieDB = new MovieDB(this);
    }

    /** on given input displays the macthed movie data */
    public void btnLookUpClick(View view) {
        Cursor cursor = movieDB.getMovieData();

        if (cursor.getCount() == 0) {
            Toast toast = Toast.makeText(this, "No Entry Exists", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String keyword = user_input.getText().toString().toLowerCase(); //user input text

            if (keyword.isEmpty()) {
                Toast toast = Toast.makeText(this, "Please enter keyword", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                ArrayList<String> title = new ArrayList<>(); //store title search results
                ArrayList<String> director = new ArrayList<>(); //store director search results
                ArrayList<String> cast = new ArrayList<>(); //store case search results

                while (cursor.moveToNext()) {
                    //check if user input is equal to title or director or cast
                    if (cursor.getString(1).toLowerCase().contains(keyword) || cursor.getString(3).toLowerCase().contains(keyword) || cursor.getString(4).toLowerCase().contains(keyword)) {
                        String[] temp = new String[3];
                        title.add(cursor.getString(1));
                        director.add(cursor.getString(3));
                        cast.add(cursor.getString(4));
                    }
                }

                //check is arraylist are empty. then displays not found
                if (title.isEmpty()) {
                    Toast toast = Toast.makeText(this, "Not found", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //calling adapter for display custom listview items on search results
                    SearchResultAdapter adapter = new SearchResultAdapter(this, title, director, cast);
                    list.setAdapter(adapter);
                }
            }
        }
    }
}