package com.example.movietracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.movietracker.R;

public class RatingsAdapter extends ArrayAdapter<String> {
    private Activity context;
    private String[] movieTitle;
    private String[] movieYear;
    private CheckBox selected;

    public RatingsAdapter (Activity context, String[] movieTitle, String[] movieYear) {
        super(context, R.layout.movie_ratings_listview, movieTitle);
        this.context = context;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.movie_ratings_listview, null,true);

        TextView textView = (TextView) rowView.findViewById(R.id.title);
        textView.setText(movieTitle[position] + " - " + movieYear[position]);

        CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkbox);
        checkbox.setId(position);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    if (selected != null) {
                        selected.setChecked(false);
                    }
                    selected = cb;
                } else {
                    selected = null;
                }
            }
        });

        return rowView;
    }

    public int getSelectedCheckBoxID() {
        if (selected != null) {
            return selected.getId();
        }
        return -1;
    }
}
