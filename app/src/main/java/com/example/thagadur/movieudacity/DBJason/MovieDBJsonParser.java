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
                movieDB.setMovieId(movieResultJsonArray.getJSONObject(i).getString("id"));
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
    /**
     * parseMovieTrailerStringToJson () is used to get the Json Object , Json arrays and we are going
     * to set the value to a list of type MovieTrailerDBs so that the data can be Viewed in the Layout
     *
     * @param movieResultData- Consits of Movie Results data in the form of String
     * @return movieTrailerList which contains all the necessary of movie Trailers details data in the ArrayList
     */
    public static List<MovieTrailerDBs> parseMovieTrailerStringToJson(String movieResultData) {
        //Log.i("Movie result Data", movieResultData);
        List<MovieTrailerDBs> movieTrailerList = new ArrayList<>();

        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);
            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("results");
            movieResultJsonArray.put(movieResultJsonObject);

            //System.out.println("size of the Json is " + movieResultData.length());
            for (int i = 0; i < movieResultJsonArray.length(); i++) {

                MovieTrailerDBs movieTrailerDB = new MovieTrailerDBs();
                movieTrailerDB.setKey(movieResultJsonArray.getJSONObject(i).get("key").toString());
                movieTrailerDB.setTrailerName(movieResultJsonArray.getJSONObject(i).get("name").toString());
                movieTrailerDB.setTrailerType(movieResultJsonArray.getJSONObject(i).get("type").toString());
                movieTrailerList.add(movieTrailerDB);
                System.out.println("Hello " + movieTrailerList.get(i).getTrailerName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieTrailerList;
    }

    /**
     * parseMovieReviewStringToJson () is used to get the Json Object , Json arrays and we are going
     * to set the value to a list of type movieReviewsDBsList so that the data can be Viewed in the Layout
     *
     * @param movieResultData- Consits of Movie Results data in the form of String
     * @return movieReviewsDBsList which contains all the necessary of movie Reviews details data in the ArrayList
     */
    public static List<MovieReviewsDBs> parseMovieReviewStringToJson(String movieResultData) {
        //Log.i("Movie result Data", movieResultData);
        List<MovieReviewsDBs> movieReviewsDBsList = new ArrayList<>();

        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);
            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("results");
            movieResultJsonArray.put(movieResultJsonObject);

            //System.out.println("size of the Json is " + movieResultData.length());
            for (int i = 0; i < movieResultJsonArray.length(); i++) {

                MovieReviewsDBs movieReviewsDBs = new MovieReviewsDBs();
                movieReviewsDBs.setAuthor(movieResultJsonArray.getJSONObject(i).get("author").toString());
                movieReviewsDBs.setContent(movieResultJsonArray.getJSONObject(i).get("content").toString());
                movieReviewsDBsList.add(movieReviewsDBs);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieReviewsDBsList;
    }
}
