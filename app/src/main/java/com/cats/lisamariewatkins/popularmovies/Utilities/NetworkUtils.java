package com.cats.lisamariewatkins.popularmovies.Utilities;

import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by lisamwatkins on 8/29/17.
 */

public class NetworkUtils {
    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String API_PARAM = "api_key";
    private static final String API_KEY = "key";
    private static final String SIZE = "w185/";

    public static URL buildMoviesUrl (String parameter){
        Uri buildUri = null;

        switch (parameter){
            case "popular":
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath("3")
                        .appendPath("movie")
                        .appendPath(POPULAR)
                        .appendQueryParameter(API_PARAM, API_KEY)
                        .build();
                break;
            case "top_rated":{
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath("3")
                        .appendPath("movie")
                        .path(TOP_RATED)
                        .appendQueryParameter(API_PARAM, API_KEY)
                        .build();
                break;
            }
        }

        URL resultUrl = null;
        try{
            resultUrl = new URL(buildUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.i("***", resultUrl.toString());
        return resultUrl;
    }

    public static Uri buildPosterUrl(String posterId){
        Uri movieUri = Uri.parse(BASE_POSTER_URL)
                .buildUpon()
                .appendPath("t")
                .appendPath("p")
                .appendPath(SIZE)
                .appendPath(posterId)
                .build();
        return movieUri;
    }


    public static String downloadUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
