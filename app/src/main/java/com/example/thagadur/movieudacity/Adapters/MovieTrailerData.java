package com.example.thagadur.movieudacity.Adapters;

/**
 * Created by Thagadur on 2/17/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thagadur.movieudacity.DBJason.MovieTrailerDBs;
import com.example.thagadur.movieudacity.R;

import java.util.List;

/**
 * RecyclerView which displays single Trailer list item
 */
public class MovieTrailerData extends RecyclerView.Adapter<MovieTrailerData.MovieViewHolder> {
    List<MovieTrailerDBs> movieTrailerList;
    Context context;

    /**
     * Constructor of MovieTrailerData
     *
     * @param context
     * @param movieTrailerList Accepting movieTrailerList ArrayList which consists of MovieTrailerDBs details
     */
    public MovieTrailerData(Context context, List<MovieTrailerDBs> movieTrailerList) {
        this.context = context;
        this.movieTrailerList = movieTrailerList;

    }

    /**
     * Setting RecyclerList View Adapter Layout
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_trailer_list, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;

    }

    /**
     * onBindViewHolder() which binds the data to the RecyclerListAdapter
     * Like MovieTrailer...
     *
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        final MovieTrailerDBs movieTrailerDBs = movieTrailerList.get(position);
        final String movieTrailerKey = movieTrailerDBs.getKey();
        final String movieTrailerName = movieTrailerDBs.getTrailerName();
        final String movieTrailerType = movieTrailerDBs.getTrailerType();

        holder.movieTrailerName.setText(movieTrailerName);
        holder.movieTrailerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + movieTrailerKey));
                context.startActivity(intent);
            }
        });
        //Picasso.with(context).load(Constant.POSTER_PATH + moviePoster).into(holder.imageViewMoviePoster);

    }

    /**
     * getItemCount() which returns movieTrailerList list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return movieTrailerList.size();
    }

    /**
     * Calling viewHolder SubClass
     */
    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTrailerName;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieTrailerName = itemView.findViewById(R.id.trailer_name);
        }
    }
}

