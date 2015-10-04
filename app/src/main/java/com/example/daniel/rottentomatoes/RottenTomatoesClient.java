package com.example.daniel.rottentomatoes;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class RottenTomatoesClient{
    private final String API_KEY = "9htuhtcb4ymusd73d4z6jxcj";
    private final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/";
    private AsyncHttpClient client;  //AsyncClient

    public RottenTomatoesClient(){
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl){
        return API_BASE_URL + relativeUrl; //creates the api url and retuns it as a string
    }

    public void getBoxOfficeMovies(JsonHttpResponseHandler handler){
        String url = getApiUrl("lists/movies/box_office.json");
        //TODO no clue what this does
        RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler); //calls get from the async client library
    }
}//end of RottenTomatoes Client
