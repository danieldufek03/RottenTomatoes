package com.example.daniel.rottentomatoes;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BoxOfficeActivity extends ActionBarActivity {
    public static final  String  MOVIE_DETAIL_KEY = "movie";
    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;
    RottenTomatoesClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);

        //Attatches the list view xml listView lvMovies to the ListView variable
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        //declares an empty arraylist of BoxOfficeMovies
        ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        //creates a new boxOfficeAdapter and passes in the arraylist aMovies
        adapterMovies = new BoxOfficeMoviesAdapter(this, aMovies);
        //Assigns adapterMovies to the ListView lvMovies
        lvMovies.setAdapter(adapterMovies);

        //Fetch Data Remotely
        fetchBoxOfficeMovies();
        setupMovieSelectedListener();
    }

   private void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new OnItemClickListener(){
            @Override
        public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId){
                Intent i = new Intent(BoxOfficeActivity.this, BoxOfficeDetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }


    private void fetchBoxOfficeMovies() {
        //assigns client to an empty RottenTomatoesClient
        client = new RottenTomatoesClient();
        //calls the getBoxOffice method from RottenTomatoesClient
        client.getBoxOfficeMovies(new JsonHttpResponseHandler(){
            @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody){
                JSONArray items = null;
                try{
                    //get the movies json array
                    items =  responseBody.getJSONArray("movies");
                    //parse json array into array of model objects
                    ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
                    //load model objects into the adapter for each movie in movies
                    for (BoxOfficeMovie movie: movies){
                        adapterMovies.add(movie); //adds a movie through the adapter
                    }
                    //TODO I don't understand this line
                    adapterMovies.notifyDataSetChanged();
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_box_office, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}//end of BoxOfficeActivity


