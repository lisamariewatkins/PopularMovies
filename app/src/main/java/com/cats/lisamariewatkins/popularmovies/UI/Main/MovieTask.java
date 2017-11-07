package com.cats.lisamariewatkins.popularmovies.UI.Main;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.cats.lisamariewatkins.popularmovies.Data.Models.Movie;
import com.cats.lisamariewatkins.popularmovies.UI.Main.AsyncTaskCompleteListener;
import com.cats.lisamariewatkins.popularmovies.Utilities.JSONUtils;
import com.cats.lisamariewatkins.popularmovies.Utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by lisa.watkins on 9/7/2017.
 */

public class MovieTask extends AsyncTask<String, Void, List<Movie>> {

    private Context mContext;
    private AsyncTaskCompleteListener<List<Movie>> mListener;
    private ProgressBar mLoadingProgressBar;

    public MovieTask(Context context, AsyncTaskCompleteListener<List<Movie>> listener,
                     ProgressBar progressBar){
        this.mContext = context;
        this.mListener = listener;
        this.mLoadingProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        if(params.length == 0){
            return null;
        }

        String sortingPreference = params[0];

        URL moviesRequest = NetworkUtils.buildMoviesUrl(sortingPreference);

        try{
            String jsonResponse = NetworkUtils.downloadUrl(moviesRequest);
            List<Movie> movieData = JSONUtils.getMovies(jsonResponse);
            return movieData;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        mListener.onTaskComplete(movies);
    }
}
