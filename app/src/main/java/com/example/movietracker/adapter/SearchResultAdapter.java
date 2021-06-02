package com.example.movietracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.movietracker.R;

import java.util.ArrayList;

public class SearchResultAdapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> title;
    private ArrayList<String> director;
    private ArrayList<String> cast;

    public SearchResultAdapter (Activity context, ArrayList<String> title, ArrayList<String> director, ArrayList<String> cast) {
        super(context, R.layout.display_movies_listview, title);
        this.context = context;
        this.title = title;
        this.director = director;
        this.cast = cast;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.search_result_listview, null,true);

        TextView textView1 = (TextView) rowView.findViewById(R.id.title);
        textView1.setText(title.get(position));

        TextView textView2 = (TextView) rowView.findViewById(R.id.director);
        textView2.setText(director.get(position));

        TextView textView3 = (TextView) rowView.findViewById(R.id.cast);
        textView3.setText(cast.get(position));

        return rowView;
    }
}
