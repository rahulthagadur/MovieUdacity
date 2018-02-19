package com.example.thagadur.movieudacity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.thagadur.movieudacity.Adapters.MovieReviewData;
import com.example.thagadur.movieudacity.Adapters.MovieTrailerData;
import com.example.thagadur.movieudacity.Constants.Constant;
import com.example.thagadur.movieudacity.DBJason.MovieDBJsonParser;
import com.example.thagadur.movieudacity.DBJason.MovieReviewsDBs;
import com.example.thagadur.movieudacity.DBJason.MovieTrailerDBs;
import com.example.thagadur.movieudacity.NetworkUtilsPackage.NetworkUtilities;
import com.example.thagadur.movieudacity.UtilsDatabase.ContentProviderUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static android.R.attr.apiKey;

/**
 * Created by thagadur on 4/1/18.
 */

public class MovieDetailActivity extends AppCompatActivity {

    TextView textViewMovieTitle, textViewMovieRating, textViewMovieSynopsis, textViewMovieReleaseDate;
    ImageView imageViewMoviePoster, favoriteImageView;
    Context context;
    String movieImagePath, movieTitle, movieSynopsis, movieId, movieRating, movieReleaseDate;
    MovieTrailerData movieTrailerData;
    MovieReviewData movieReviewData;
    List<MovieTrailerDBs> movieTrailerDBs;
    List<MovieReviewsDBs> movieReviewsDBs;
    boolean favMovie = false;
    RecyclerView movieTrailer, movieReview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisationOfObjects();
        String trailerUrl = Constant.END_POINT + "movie/" + movieId + "/videos" + apiKey;
        String reviewUrl = Constant.END_POINT + "movie/" + movieId + "/reviews" + apiKey;
        readMovieData();
        loadMovieData();
        checkForFavorite(movieId);
        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favMovie) {
                    deleteFromFavList(movieId);

                } else {
                    insertToFavList();
                    // Toast.makeText(context, "ID="+db.addMovie(movie), Toast.LENGTH_SHORT).show();
                }
                checkForFavorite(movieId);
            }
        });
        loadMovieTrailerData(trailerUrl);
        laodMovieReviewData(reviewUrl);
    }

    /**
     * Here initialized all the necessary objects,member variables and layout
     */
    public void initialisationOfObjects() {
        setContentView(R.layout.movie_details_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        context = this;
        textViewMovieRating = (TextView) findViewById(R.id.textView_movie_rating);
        textViewMovieReleaseDate = (TextView) findViewById(R.id.textView_movie_release_date);
        textViewMovieTitle = (TextView) findViewById(R.id.textView_movie_title);
        textViewMovieSynopsis = (TextView) findViewById(R.id.textView_movie_synopsis);
        imageViewMoviePoster = (ImageView) findViewById(R.id.imageView_movie_image_poster);
        favoriteImageView = (ImageView) findViewById(R.id.favourite_image_view);

        movieTrailer = (RecyclerView) findViewById(R.id.trailer_list);
        LinearLayoutManager linearLayoutManagerTrailer = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        movieTrailer.setLayoutManager(linearLayoutManagerTrailer);

        movieReview = (RecyclerView) findViewById(R.id.trailer_list);
        LinearLayoutManager linearLayoutManagerReview = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        movieTrailer.setLayoutManager(linearLayoutManagerTrailer);

    }

    /**
     * Here read the movieDB Data from Bundle
     */
    public void readMovieData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            movieId = bundle.getString(Constant.MOVIE_ID);
            movieTitle = bundle.getString(Constant.MOVIE_TITLE);
            movieRating = bundle.getString(Constant.MOVIE_RATING);
            movieImagePath = bundle.getString(Constant.MOVIE_IMAGE_POSTER);
            movieSynopsis = bundle.getString(Constant.MOVIE_SYNOPSIS);
            movieReleaseDate = bundle.getString(Constant.MOVIE_ReleaseDate);
        } else {
        }
    }

    /**
     * Here layout field are set with respective data
     */
    public void loadMovieData() {
        textViewMovieRating.setText(movieRating + "/10");
        textViewMovieTitle.setText(movieTitle);
        textViewMovieReleaseDate.setText(movieReleaseDate);
        textViewMovieSynopsis.setText(movieSynopsis);
        Picasso.with(context).load(Constant.POSTER_PATH + movieImagePath).into(imageViewMoviePoster);
    }

    public void loadMovieTrailerData(String movieTrailerUrl) {
        URL url = NetworkUtilities.buildUrl(movieTrailerUrl);
        new RequestMovieTrailerdata().execute(url);
    }

    public void laodMovieReviewData(String movieReviewsUrl) {
        URL url = NetworkUtilities.buildUrl(movieReviewsUrl);
        new RequestMovieReviewdata().execute(url);
    }

    private void loadMovieTrailerAdapter(String movieResponsePosterData) {
        movieTrailerDBs = MovieDBJsonParser.parseMovieTrailerStringToJson(movieResponsePosterData);
        setTrailerIntoLayoutFields(movieTrailerDBs);
    }

    private void loadMovieReviewAdapter(String movieResponsePosterData) {
        movieReviewsDBs = MovieDBJsonParser.parseMovieReviewStringToJson(movieResponsePosterData);
        setReviewsIntoLayoutFields(movieReviewsDBs);
    }

    public void setTrailerIntoLayoutFields(List<MovieTrailerDBs> movieTrailerList) {
        movieTrailerData = new MovieTrailerData(context, movieTrailerList);
        movieTrailer.setAdapter(movieTrailerData);
    }

    public void setReviewsIntoLayoutFields(List<MovieReviewsDBs> movieReviewsList) {
        movieReviewData = new MovieReviewData(context, movieReviewsList);
        movieReview.setAdapter(movieReviewData);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void checkForFavorite(String id) {
        Uri uri = ContentProviderUtils.MovieTuple.CONTENT_URI;
        final String[] projection = ContentProviderUtils.MovieTuple.COLUMNS;
        Boolean check = false;
        Cursor cursor = getContentResolver().query(uri, projection, "movie_id=?", new String[]{movieId}, null);
        if (cursor.moveToFirst()) {
            check = true;
        }
        if (check) {
            favoriteImageView.setImageResource(R.drawable.favorite_enable_normal);
            favMovie = true;

        } else {
            favoriteImageView.setImageResource(R.drawable.favorite_disable_normal);
            favMovie = false;
        }


    }

    private void insertToFavList() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContentProviderUtils.MovieTuple.COLUMN_TITLE, movieTitle);
        contentValues.put(ContentProviderUtils.MovieTuple.COLUMN_POSTER_PATH, movieImagePath);
        contentValues.put(ContentProviderUtils.MovieTuple.COLUMN_DETAILS, movieSynopsis);
        contentValues.put(ContentProviderUtils.MovieTuple.COLUMN_RATING, movieRating);
        contentValues.put(ContentProviderUtils.MovieTuple.COLUMN_RELEASE_DATE, movieReleaseDate);
        contentValues.put(ContentProviderUtils.MovieTuple.COLUMN_MOVIE_ID, movieId);
        Uri uri = getContentResolver().insert(ContentProviderUtils.MovieTuple.CONTENT_URI, contentValues);
        Log.v("Inserting Error", uri.toString());
        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }


    }


    private void deleteFromFavList(String movieId) {
        Uri uri = ContentProviderUtils.MovieTuple.CONTENT_URI;
        // uri = uri.buildUpon().appendPath(movieId).build();
        int taskDeleted = getContentResolver().delete(uri, "movie_id=?",
                new String[]{movieId});
        Toast.makeText(getApplicationContext(), "Deleted=" + taskDeleted, Toast.LENGTH_LONG).show();
        Log.v("Deleting Error", String.valueOf(taskDeleted));
    }

    /**
     * Requesting Trailer Details data from the api and data has been send to loadMovieTrailerAdapter in
     * String form for Json conversion
     */
    class RequestMovieTrailerdata extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            String movieTrailerResponseData = null;
            URL url = urls[0];
            try {
                movieTrailerResponseData = NetworkUtilities.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ErrorMessage", e.getMessage());
            }
            return movieTrailerResponseData;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String movieResponseData) {
            super.onPostExecute(movieResponseData);
            Log.d("Data", movieResponseData);
            if (movieResponseData != null) {
                loadMovieTrailerAdapter(movieResponseData);
            }
        }
    }

    /**
     * Requesting Trailer Details data from the api and data has been send to loadMovieTrailerAdapter in
     * String form for Json conversion
     */
    class RequestMovieReviewdata extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            String movieReviewResponseData = null;
            URL url = urls[0];
            try {
                movieReviewResponseData = NetworkUtilities.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ErrorMessage", e.getMessage());
            }
            return movieReviewResponseData;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String movieResponseData) {
            super.onPostExecute(movieResponseData);
            Log.d("Data", movieResponseData);
            if (movieResponseData != null) {
                loadMovieReviewAdapter(movieResponseData);
            }
        }
    }

}
