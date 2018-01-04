package com.example.thagadur.movieudacity.DBJason;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thagadur on 3/1/18.
 */


public class MovieDBJsonParser {


    /**
     * movieStringToJson() which converts String to Json Object and stored to list
     *
     * @param movieResultData
     * @return
     */
    public static  List<MovieDB> movieStringToJson(String movieResultData) {
        List<MovieDB> movieDBList = new ArrayList<>();
        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);

            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("results");
            for (int i = 0; i < movieResultJsonArray.length(); i++) {
                MovieDB movieDB = new MovieDB();
                movieDB.setMovieTitle(movieResultJsonArray.getJSONObject(i).getString("original_title"));
                movieDB.setImagePath(movieResultJsonArray.getJSONObject(i).getString("poster_path"));
                movieDB.setMovieRating(movieResultJsonArray.getJSONObject(i).getString("vote_average"));
                movieDB.setMovieSynopsis(movieResultJsonArray.getJSONObject(i).getString("overview"));
                movieDB.setMovieReleaseDate(movieResultJsonArray.getJSONObject(i).getString("release_date"));
                movieDBList.add(movieDB);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieDBList;
    }

}
