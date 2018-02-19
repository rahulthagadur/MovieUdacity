package com.example.thagadur.movieudacity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.thagadur.movieudacity.Constants.Constant;
import com.example.thagadur.movieudacity.DBJason.MovieDB;
import com.example.thagadur.movieudacity.MovieDetailActivity;
import com.example.thagadur.movieudacity.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by thagadur on 4/1/18.
 */

public class MovieList extends RecyclerView.Adapter<MovieList.MovieViewContentHolder> {

    List<MovieDB> movieDBList;
    Context context;

    public MovieList(Context context, List<MovieDB> movieDBList) {
        this.context = context;
        this.movieDBList = movieDBList;

    }


    @Override
    public MovieViewContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_image_card_view_adapter, parent, false);
        MovieViewContentHolder viewContentHolder = new MovieViewContentHolder(view);
        return viewContentHolder;
    }


    @Override
    public void onBindViewHolder(MovieViewContentHolder holder, int position) {
        MovieDB movieDB = movieDBList.get(position);
        final String movieId= movieDB.getMovieId();
        final String moviePoster = movieDB.getImagePath();
        final String movieTitle = movieDB.getMovieTitle();
        final String movieSynopsis = movieDB.getMovieSynopsis();
        final String movieReleaseDate = movieDB.getMovieReleaseDate();
        final String movieRating = movieDB.getMovieRating();
        Picasso.with(context).load(Constant.POSTER_PATH + moviePoster).into(holder.moviePoster );

        holder.moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMovieDetailedView = new Intent();
                intentMovieDetailedView.putExtra(Constant.MOVIE_ID,movieId);
                intentMovieDetailedView.putExtra(Constant.MOVIE_TITLE, movieTitle);
                intentMovieDetailedView.putExtra(Constant.MOVIE_IMAGE_POSTER, moviePoster);
                intentMovieDetailedView.putExtra(Constant.MOVIE_SYNOPSIS, movieSynopsis);
                intentMovieDetailedView.putExtra(Constant.MOVIE_RATING, movieRating);
                intentMovieDetailedView.putExtra(Constant.MOVIE_ReleaseDate, movieReleaseDate);
                intentMovieDetailedView.setClass(context, MovieDetailActivity.class);
                context.startActivity(intentMovieDetailedView);
            }
        });
    }

    /**
     * getItemCount() which returns movieDBList list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return movieDBList.size();
    }

    /**
     * Calling viewHolder SubClass
     */
    class MovieViewContentHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster ;

        public MovieViewContentHolder(View itemView) {
            super(itemView);
            // Initializing  imageViewMoviePoster layout field
            moviePoster = itemView.findViewById(R.id.imageView_movie_poster);
        }
    }
}
