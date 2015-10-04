package com.example.daniel.rottentomatoes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


/*
New to adapters  Adapters are a bridge between Models and Views
so this adapter is the bridge between BoxOfficeMovie and the boxOffice xml files
 */
public class BoxOfficeMoviesAdapter extends ArrayAdapter<BoxOfficeMovie> {
    //constructor for the adapter, takes in the current context and  an ArrayList of BoxOffice movies
    public BoxOfficeMoviesAdapter(Context context, ArrayList<BoxOfficeMovie> aMovies){
        super (context, 0 , aMovies);//calls the ArrayAdapter Constructor and feeds it the arguments from our constructor
    }

    @Override
    //overrides the ArrayAdapter getView method
    public View getView(int position, View convertView, ViewGroup parent){
        //get data item for this position
        BoxOfficeMovie movie = getItem(position);
        //check if existing view is being reused, if not inflate the view
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_box_office_movie, parent, false);
        }
        //lookup views within item lookup
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        TextView tvCast = (TextView) convertView.findViewById(R.id.tvCast);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        //populate the data into the template using the data object
        tvTitle.setText(movie.getTitle()); //calls setText from the TextView Class
        tvCriticsScore.setText("Score: " + movie.getCriticsScore() + "%");
        tvCast.setText(movie.getCastList());
        Picasso.with(getContext()).load(movie.getPosterUrl()).into(ivPosterImage);
        //return completed view to render on screen

        return convertView; //returns the View
    }
}
