/** display movie image on imageview */

package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieImage extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    private static byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_image);

        textView = (TextView) findViewById(R.id.tvMovieTitle);
        imageView = (ImageView) findViewById(R.id.ivMovieImage);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");

        textView.setText(title);

        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length); //decode byte array to bitmap image
        imageView.setImageBitmap(bmp);
    }

    //set encoded image byte data
    public static void setImage(byte[] image) {
        MovieImage.image = image;
    }
}