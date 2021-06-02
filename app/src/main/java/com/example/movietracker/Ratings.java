package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movietracker.adapter.RatingsAdapter;
import com.example.movietracker.db.MovieDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Ratings extends AppCompatActivity {

    ListView list;
    MovieDB movieDB;
    RatingsAdapter adapter;
    String[] movieTitle;
    String[] movieYear;
    String APIKEY = "k_rsxnb7mi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        list = (ListView) findViewById(R.id.list5);

        movieDB = new MovieDB(this);

        Cursor cursor = movieDB.getMovieData();

        if (cursor.getCount() == 0) {
            Toast toast = Toast.makeText(this, "No entry exists", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            movieTitle = new String[cursor.getCount()]; //stores all movie titles in db
            movieYear = new String[cursor.getCount()]; //stores all movie id in db
            int count = 0;

            while (cursor.moveToNext()) {
                movieTitle[count] = cursor.getString(1);
                movieYear[count] = cursor.getString(2);
                count++;
            }

            adapter = new RatingsAdapter(this, movieTitle, movieYear);
            list.setAdapter(adapter);
        }

    }

    /** on find in imdb button click get rating results for selected movie */
    public void btnFindInIMDBClick(View view) {
        int selected = adapter.getSelectedCheckBoxID(); //returns -1 if movie is not selected else return movie id in db
        if (selected == -1) {
            Toast toast = Toast.makeText(this, "Please select a movie", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Thread thread = new Thread(new Runnable() {
                ArrayList<String> id; //stores returned movie id
                ArrayList<String> titles; //stores returned movie title
                ArrayList<String> rating; //stores returned rating
                ArrayList<byte[]> images; //stores returned encoded image data

                @Override
                public void run() {
                    try {
                        System.out.println(movieTitle[selected] + " - " + movieYear[selected]);
                        String movie_name = movieTitle[selected]; //stores selected movie title
                        String movie_year = movieYear[selected]; //stores selected movie year

                        String baseURL_1 = "https://imdb-api.com/en/API/SearchTitle/" + APIKEY + "/" + movie_name + " " + movie_year;
                        Uri builtURI_1 = Uri.parse(baseURL_1); //convert to url
                        URL requestURL_1 = new URL(builtURI_1.toString());
                        HttpURLConnection con_1 = (HttpURLConnection) requestURL_1.openConnection(); //establishing connection with the API
                        BufferedReader bf_1 = new BufferedReader(new InputStreamReader(con_1.getInputStream()));
                        StringBuilder stb_1 = new StringBuilder("");
                        String line_1;
                        while ((line_1 = bf_1.readLine()) != null) {
                            stb_1.append(line_1);
                        }
                        JSONObject json_1 = new JSONObject(stb_1.toString()); //convert returned string data to JsonObj

                        System.out.println(stb_1.toString());

                        JSONArray json_movies = json_1.getJSONArray("results"); //get 'results' form json obj

                        id = new ArrayList<>();
                        titles = new ArrayList<>();
                        rating = new ArrayList<>();
                        images = new ArrayList<>();

                        for (int i = 0; i < json_movies.length(); i++) {
                            JSONObject resultData = json_movies.getJSONObject(i);
                            id.add(resultData.getString("id"));
                            titles.add(resultData.getString("title"));

                            //getting movie image
                            URL imgUrl = new URL(resultData.getString("image"));
                            HttpURLConnection con_2 = (HttpURLConnection) imgUrl.openConnection();
                            con_2.setDoInput(true);
                            con_2.connect();
                            InputStream is = con_2.getInputStream();
                            Bitmap bmp = BitmapFactory.decodeStream(is);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);
                            byte[] byteArray = stream.toByteArray();
                            images.add(byteArray);
                        }

                        for (int i = 0; i < id.size(); i++) {
                            //getting movie ratings for search results
                            System.out.println("requesting " + id.get(i));
                            String baseURL_2 = "https://imdb-api.com/en/API/UserRatings/" + APIKEY + "/" + id.get(i);
                            Uri builtURI_2 = Uri.parse(baseURL_2);
                            URL requestURL_2 = new URL(builtURI_2.toString());
                            HttpURLConnection con_2 = (HttpURLConnection) requestURL_2.openConnection();
                            BufferedReader bf_2 = new BufferedReader(new InputStreamReader(con_2.getInputStream()));
                            StringBuilder stb_2 = new StringBuilder("");
                            String line_2;
                            while ((line_2 = bf_2.readLine()) != null) {
                                stb_2.append(line_2);
                            }

                            System.out.println(stb_2.toString());

                            JSONObject json_2 = new JSONObject(stb_2.toString());
                            String movie_rating = json_2.getString("totalRating");

                            rating.add(movie_rating);

                        }

                    } catch (Exception e) {
                        System.out.println("Error in getting movie information");
                        Log.d("String Key", "Error in getting movie information");
                    }

                    //update UI based on returned results
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(images);
                            Intent intent = new Intent(getApplicationContext(), RatingResults.class);
                            intent.putExtra("Title", titles);
                            intent.putExtra("Rating", rating);
                            RatingResults.setImages(images);
                            startActivity(intent);
                        }
                    });
                }
            });
            thread.start();
        }
    }
}