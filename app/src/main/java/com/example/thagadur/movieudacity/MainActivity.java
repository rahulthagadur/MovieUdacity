package com.example.thagadur.movieudacity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.thagadur.movieudacity.Adapters.MovieList;
import com.example.thagadur.movieudacity.Constants.Constant;
import com.example.thagadur.movieudacity.DBJason.MovieDB;
import com.example.thagadur.movieudacity.DBJason.MovieDBJsonParser;
import com.example.thagadur.movieudacity.NetworkUtilsPackage.NetworkUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerViewMovieList;
    Context context;
    MovieList movieList;
    List<MovieDB> movieDBList;
    String movieDbUrl;
    String movieSort;
    String movieUrlQuery;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisationOfObjects();
        movieUrlQuery = movieDbUrl + movieSort;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!checkOnline()) {
            Intent intent = new Intent();
            intent.setClass(this, OffLine.class);
            startActivity(intent);
            finish();
        } else {
            loadMovieData(movieUrlQuery);
        }
    }

    public boolean checkOnline() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Here initialized all the necessary objects,member variables and layout
     */
    public void initialisationOfObjects() {
        setContentView(R.layout.activity_main);
        movieDbUrl = Constant.END_POINT;
        movieSort = Constant.SORT_BY_POPULAR;
        context = this;
        movieDBList = new ArrayList<>();
        recyclerViewMovieList = (RecyclerView) findViewById(R.id.movie_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerViewMovieList.setLayoutManager(layoutManager);
    }

    public void loadMovieData(String movieDbUrl) {
        URL url = NetworkUtilities.buildUrl(movieDbUrl);
        new RequestMovieDbData().execute(url);
    }


    public void loadMovieAdapter(String resultData) {
        movieDBList = MovieDBJsonParser.movieStringToJson(resultData);
        movieList = new MovieList(context, movieDBList);
        recyclerViewMovieList.setAdapter(movieList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.sort_by_top_rated) {
            movieSort = Constant.SORT_BY_TOP_RATED;
            String movieUrlQuery = movieDbUrl + movieSort;
            loadMovieData(movieUrlQuery);
        } else if (selectedItemId == R.id.sort_by_popular) {
            movieSort = Constant.SORT_BY_POPULAR;
            String movieUrlQuery = movieDbUrl + movieSort;
            loadMovieData(movieUrlQuery);
        }
        return super.onOptionsItemSelected(item);
    }

    class RequestMovieDbData extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String movieResponseData = null;
            URL url = urls[0];
            try {
                movieResponseData =  NetworkUtilities.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ErrorMessage", e.getMessage());
            }
            return movieResponseData;
        }

        @Override
        protected void onPostExecute(String movieResponseData) {
            super.onPostExecute(movieResponseData);
            if (movieResponseData != null) {
                loadMovieAdapter(movieResponseData);
            }
        }
    }
}
