//Java container for the JSON data

package com.example.daniel.rottentomatoes;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class BoxOfficeMovie implements Serializable{
    private final static long serialVersionUID = -8959832007991513854L;
    private String title;
    private int year;
    private String synopsis;
    private String posterUrl;
    private int criticsScore;
    private ArrayList<String> castList;

    private String largePosterURl;
    private String criticsConsensus;
    private int audienceScore;

    public String getTitle(){
        return title;
    }
    public int getYear(){
        return year;
    }
    public String getSynopsis(){
        return synopsis;
    }
    public String getPosterUrl(){
        return posterUrl;
    }
    public int getCriticsScore(){
        return criticsScore;
    }
    public String getCastList(){
        return TextUtils.join(", ", castList);
    }
    public String getLargePosterURl(){
        return largePosterURl;
    }
    public String getCriticsConsensus(){
        return criticsConsensus;
    }
    public int getAudienceScore(){
        return audienceScore;
    }

    //fromJson takes a JSONObject as an argument and returns a BoxOfficeMovie
    //if given a JSonObject
    public static BoxOfficeMovie fromJson(JSONObject jsonObject){
        BoxOfficeMovie b = new BoxOfficeMovie(); //instance of BoxOfficeMovie to put data in
        try{
            //deserialize json into object fields
            b.title = jsonObject.getString("title");
            b.year = jsonObject.getInt("year");
            b.synopsis = jsonObject.getString("synopsis");
            b.posterUrl = jsonObject.getJSONObject("posters").getString("thumbnail");
            b.criticsScore = jsonObject.getJSONObject("ratings").getInt("critics_score");
            //constuct array of cast names
            b.castList = new ArrayList<String>(); //creates an empty arraylist of castnames
            b.largePosterURl = jsonObject.getJSONObject("posters").getString("detailed");
            b.criticsConsensus = jsonObject.getString("critics_consensus");
            b.audienceScore = jsonObject.getJSONObject("ratings").getInt("audience_score");
            JSONArray abridgedCast = jsonObject.getJSONArray("abridged_cast"); //jsonArray of castmembers
            for (int i = 0; i < abridgedCast.length(); i++){
                b.castList.add(abridgedCast.getJSONObject(i).getString("name"));//JsonArray to ArrayList
            }
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return b; //returns the BoxOfficeMovie
    }

    //Returns an ArrayList of BoxOfficeMovies if given a jsonArray
    public static ArrayList<BoxOfficeMovie> fromJson(JSONArray jsonArray){
        ArrayList<BoxOfficeMovie> movies = new ArrayList<BoxOfficeMovie>(jsonArray.length());
        //process each result in a Json Array, decode and convert to movie
        //object
        for(int i = 0; i < jsonArray.length(); i++){ //for each item in the jsonArray
            JSONObject moviesJson = null;
            try{
                moviesJson = jsonArray.getJSONObject(i); //attempt to get the individual jsonObject
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

           //feeds the JsonObject to the fromJson function and assigns it to a BoxofficeMovie "movie"
            BoxOfficeMovie movie = BoxOfficeMovie.fromJson(moviesJson);
            if (movie!= null){
                movies.add(movie); //if not null add the boxofficemovie to the arraylist movies
            }
        }
        return movies; //return the arraylist
    }
}//end of BoxOfficeMovie
