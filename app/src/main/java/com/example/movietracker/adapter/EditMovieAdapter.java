package com.example.movietracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.movietracker.R;

public class EditMovieAdapter extends ArrayAdapter<String> {
    private Activity context;
    private String[] movieTitle;
    private String[] movieID;

    public EditMovieAdapter (Activity context, String[] movieTitle, String[] movieID) {
        super(context, R.layout.edit_movie_listview, movieTitle);
        this.context = context;
        this.movieTitle = movieTitle;
        this.movieID = movieID;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.edit_movie_listview, null,true);

        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        textView.setId(Integer.parseInt(movieID[position]));
        textView.setText(movieTitle[position]);

        return rowView;
    }
}
