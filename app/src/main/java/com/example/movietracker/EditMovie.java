/**this class is for edit movie data that previous entered*/

package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movietracker.adapter.EditMovieAdapter;
import com.example.movietracker.db.MovieDB;

public class EditMovie extends AppCompatActivity {

    ListView list;
    MovieDB movieDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        list = (ListView) findViewById(R.id.list2);

        movieDB = new MovieDB(this);

        Cursor cursor = movieDB.getMovieData();

        if (cursor.getCount() == 0) {
            Toast toast = Toast.makeText(this, "No entry exists", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String[] movieTitle = new String[cursor.getCount()]; //store movie title which returns from db
            String[] movieID = new String[cursor.getCount()]; //store movie id which returns from db
            int count = 0;

            while (cursor.moveToNext()) {
                movieID[count] = cursor.getString(0);
                movieTitle[count] = cursor.getString(1);
                count++;
            }

            //calling custom listview adapter to display all movies
            EditMovieAdapter adapter = new EditMovieAdapter(this, movieTitle, movieID);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println(parent.getItemAtPosition(position));
                    Cursor cursor = movieDB.getMovieData();
                    while (cursor.moveToNext()) {
                        //finding selected movie id
                        String movie_id = cursor.getString(0);
                        if (parent.getItemAtPosition(position).equals(cursor.getString(1))) {
                            Intent intent = new Intent(EditMovie.this, EditMovieDetails.class);
                            intent.putExtra("Movie_ID", movie_id);
                            startActivity(intent);
                            break;
                        }
                    }
                    cursor.close();
                }
            });
        }
    }
}