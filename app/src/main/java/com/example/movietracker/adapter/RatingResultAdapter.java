package com.example.movietracker.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movietracker.MovieImage;
import com.example.movietracker.R;

import java.util.ArrayList;

public class RatingResultAdapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> titles;
    private ArrayList<String> rating;
    private ArrayList<byte[]> images;

    public RatingResultAdapter (Activity context, ArrayList<String> titles, ArrayList<String> rating, ArrayList<byte[]> images) {
        super(context, R.layout.rating_result_listview, titles);
        this.context = context;
        this.titles = titles;
        this.rating = rating;
        this.images = images;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.rating_result_listview, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.thumbnail);
        Bitmap bmp = BitmapFactory.decodeByteArray(images.get(position), 0, images.get(position).length);
        imageView.setImageBitmap(bmp);

        TextView textView1 = (TextView) rowView.findViewById(R.id.title);
        textView1.setId(position);
        textView1.setText(titles.get(position));

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                Intent intent = new Intent(context, MovieImage.class);
                intent.putExtra("Title", textView.getText());
                MovieImage.setImage(images.get(textView.getId()));
                context.startActivity(intent);
            }
        });

        TextView textView2 = (TextView) rowView.findViewById(R.id.rating);
        textView2.setText("IMDB Rating " + rating.get(position));

        return rowView;
    }
}
