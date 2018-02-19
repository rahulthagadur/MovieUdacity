package com.example.thagadur.movieudacity.UtilsDatabase;


import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by Thagadur on 2/17/2018.
 */

public class ContentProviderUtils {

    public static final String DOMAIN="com.example.thagadur";
    public static final Uri BUILD_URI= Uri.parse("content://"+DOMAIN);
    public static final String MOVIE_PATH="movie";

    public static final class MovieTuple implements BaseColumns{
        public static final Uri CONTENT_URI= BUILD_URI.buildUpon().appendPath(MOVIE_PATH).build();

        public static final String TABLE_NAME = "Favorite_list_table";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_DETAILS = "movie_details";
        public static final String COLUMN_RATING = "movie_rating";



        public static final String[] COLUMNS={
                COLUMN_MOVIE_ID,
                COLUMN_TITLE,
                COLUMN_RELEASE_DATE,
                COLUMN_POSTER_PATH,
                COLUMN_DETAILS,
                COLUMN_RATING

        };
    }

}
