package com.example.thagadur.movieudacity.NetworkUtilsPackage;

import android.net.Uri;

import com.example.thagadur.movieudacity.Constants.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by thagadur on 4/1/18.
 */

public class NetworkUtilities {


    public static URL buildUrl(String movieDbQuery) {
        Uri movieDbUri = Uri.parse(movieDbQuery).buildUpon()
                .appendQueryParameter(Constant.PARAM_QUERY, Constant.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(movieDbUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static  String getResponseFromMovieDb(URL url) throws IOException {
        String movieResponseResult = null;
        HttpURLConnection movieHttpUrlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream movieInputStream = movieHttpUrlConnection.getInputStream();
            Scanner scanner = new Scanner(movieInputStream);
            scanner.useDelimiter("\\A");
            boolean hashInput = scanner.hasNext();
            if (hashInput) {
                movieResponseResult = scanner.next();
            }
        } finally {
            movieHttpUrlConnection.disconnect();
        }
        return movieResponseResult;
    }
}
