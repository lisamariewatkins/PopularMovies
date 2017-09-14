package com.cats.lisamariewatkins.popularmovies.Utilities;

import android.net.Uri;
import com.cats.lisamariewatkins.popularmovies.BuildConfig;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;



public class NetworkUtils {
    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String MOVIE_PATH = "3/movie";
    private static final String IMAGE_PATH = "t/p";
    private static final String BASE_POSTER_URL = "http://image.tmdb.org";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String API_PARAM = "api_key";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String SIZE = "w185";

    public static URL buildMoviesUrl (String parameter){
        Uri buildUri = null;

        switch (parameter){
            case "popular":
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendEncodedPath(MOVIE_PATH)
                        .appendPath(POPULAR)
                        .appendQueryParameter(API_PARAM, API_KEY)
                        .build();
                break;
            case "top_rated":{
                Log.i("***", "top rated is called in network utils");
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendEncodedPath(MOVIE_PATH)
                        .appendPath(TOP_RATED)
                        .appendQueryParameter(API_PARAM, API_KEY)
                        .build();
                Log.i("***", buildUri.toString());
                break;
            }
        }

        URL resultUrl = null;
        try{
            resultUrl = new URL(buildUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return resultUrl;
    }

    public static Uri buildPosterUrl(String posterId){
        return Uri.parse(BASE_POSTER_URL)
                .buildUpon()
                .appendEncodedPath(IMAGE_PATH)
                .appendPath(SIZE)
                .appendEncodedPath(posterId)
                .build();
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
