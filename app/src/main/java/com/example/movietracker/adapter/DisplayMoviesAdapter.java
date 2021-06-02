package com.example.movietracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.movietracker.R;

import java.util.ArrayList;

public class DisplayMoviesAdapter extends ArrayAdapter<String> {
    private Activity context;
    private String[] movieTitle;
    ArrayList<String> favouriteMovies;

    public DisplayMoviesAdapter (Activity context, String[] movieTitle, ArrayList<String> favouriteMovies) {
        super(context, R.layout.display_movies_listview, movieTitle);
        this.context = context;
        this.movieTitle = movieTitle;
        this.favouriteMovies = favouriteMovies;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.display_movies_listview, null,true);

        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        textView.setText(movieTitle[position]);

        CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkbox);
        checkbox.setTag(movieTitle[position]);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    favouriteMovies.add(checkbox.getTag().toString());
                } else {
                    favouriteMovies.remove(checkbox.getTag().toString());
                }
            }
        });

        return rowView;
    }
}
