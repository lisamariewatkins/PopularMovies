package com.cats.lisamariewatkins.popularmovies.Utilities;

import android.content.Context;

import com.cats.lisamariewatkins.popularmovies.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisamwatkins on 8/30/17.
 */

public class JSONUtils {
    public static List<Movie> getMovie(Context context, String JsonStr) throws JSONException{
        final String OWM_LIST = "list";
        final String OWM_MESSAGE_CODE = "cod";
        List<Movie> parsedMovieData = new ArrayList<Movie>();

        JSONObject movieJson = new JSONObject(JsonStr);

        if(movieJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode){
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(OWM_LIST);

        for(int i = 0; i < movieArray.length(); i++){
            Movie movie = new Movie();
            JSONObject movieObject = movieArray.getJSONObject(i);
            String posterPath = movieObject.getString("poster_path");
            movie.setPosterPath(posterPath);
            parsedMovieData.add(movie);
        }

        return parsedMovieData;
    }
}
