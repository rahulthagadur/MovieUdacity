package com.example.thagadur.movieudacity.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thagadur.movieudacity.DBJason.MovieReviewsDBs;
import com.example.thagadur.movieudacity.R;

import java.util.List;

/**
 * Created by Thagadur on 2/19/2018.
 */

public class MovieReviewData extends RecyclerView.Adapter<MovieReviewData.MovieViewHolder> {
    List<MovieReviewsDBs> movieReviewsDBsList;
    Context context;

    public MovieReviewData(Context context, List<MovieReviewsDBs> movieReviewList) {

        this.context = context;
        this.movieReviewsDBsList = movieReviewList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list, viewGroup, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
        final MovieReviewsDBs movieReviewsDBs=movieReviewsDBsList.get(i);
        final String author = movieReviewsDBs.getAuthor();
        final String content = movieReviewsDBs.getContent();
        movieViewHolder.reviewAuthor.setText(author);
        movieViewHolder.reviewContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return movieReviewsDBsList.size();
    }


    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthor,reviewContent;

        public MovieViewHolder(View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.author_textView);
            reviewContent = itemView.findViewById(R.id.content_textView);
        }
    }
}
