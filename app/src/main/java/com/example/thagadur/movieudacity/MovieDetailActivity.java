package com.example.thagadur.movieudacity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thagadur.movieudacity.Constants.Constant;
import com.squareup.picasso.Picasso;

/**
 * Created by thagadur on 4/1/18.
 */

public class MovieDetailActivity extends AppCompatActivity {

    TextView textViewMovieTitle,textViewMovieRating,textViewMovieSynopsis,textViewMovieReleaseDate;
    ImageView imageViewMoviePoster;
    Context context;
    String movieImagePath,movieTitle,movieSynopsis,movieRating,movieReleaseDate;;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisationOfObjects();
        readMovieData();
        loadMovieData();
    }

    /**
     * Here initialized all the necessary objects,member variables and layout
     */
    public void initialisationOfObjects() {
        setContentView(R.layout.movie_details_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        context = this;
        textViewMovieRating=(TextView)findViewById(R.id.textView_movie_rating);
        textViewMovieReleaseDate=(TextView)findViewById(R.id.textView_movie_release_date);
        textViewMovieTitle=(TextView)findViewById(R.id.textView_movie_title);
        textViewMovieSynopsis=(TextView)findViewById(R.id.textView_movie_synopsis);
        imageViewMoviePoster=(ImageView) findViewById(R.id.imageView_movie_image_poster);
    }

    /**
     * Here read the movieDB Data from Bundle
     */
    public void readMovieData()
    {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            movieTitle=bundle.getString(Constant.MOVIE_TITLE);
            movieRating=bundle.getString(Constant.MOVIE_RATING);
            movieImagePath=bundle.getString(Constant.MOVIE_IMAGE_POSTER);
            movieSynopsis=bundle.getString(Constant.MOVIE_SYNOPSIS);
            movieReleaseDate=bundle.getString(Constant.MOVIE_ReleaseDate);
        }
        else
        {


        }
    }

    /**
     * Here layout field are set with respective data
     */
    public void loadMovieData() {
        textViewMovieRating.setText(movieRating+"/10");
        textViewMovieTitle.setText(movieTitle);
        textViewMovieReleaseDate.setText(movieReleaseDate);
        textViewMovieSynopsis.setText(movieSynopsis);
        Picasso.with(context).load(Constant.POSTER_PATH + movieImagePath).into(imageViewMoviePoster);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
