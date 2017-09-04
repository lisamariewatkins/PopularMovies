package com.cats.lisamariewatkins.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.cats.lisamariewatkins.popularmovies.Utilities.JSONUtils;
import com.cats.lisamariewatkins.popularmovies.Utilities.NetworkUtils;

import com.cats.lisamariewatkins.popularmovies.Models.Movie;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.onMovieClickHandler{
    private RecyclerView mMovieRecylerView;
    private MoviesAdapter mMoviesAdapter;
    private TextView mErrorTextView;
    private ProgressBar mLoadingProgressBar;
    private static final int NUMBER_OF_COLUMNS = 3;
    private static final String DEFAULT_SORTING = "popular";
    private static final String TOP_RATED = "top_rated";
    private String sortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieRecylerView = (RecyclerView) findViewById(R.id.movies_rv);
        mErrorTextView = (TextView) findViewById(R.id.error_message);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.loading_progress);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mMovieRecylerView.setLayoutManager(layoutManager);
        mMovieRecylerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);
        mMovieRecylerView.setAdapter(mMoviesAdapter);

        loadMovieData();
    }

    @Override
    public void onClick(Movie targetMovie) {
        Class targetActivity = MovieDetailActivity.class;
        Context context = MainActivity.this;
        Intent intent = new Intent(context, targetActivity);

        intent.putExtra("poster_path", targetMovie.getPosterPath());
        intent.putExtra("title", targetMovie.getTitle());
        intent.putExtra("overview", targetMovie.getOverview());
        intent.putExtra("rating", targetMovie.getUserRating());
        intent.putExtra("release_date", targetMovie.getReleaseDate());

        startActivity(intent);
    }

    private void loadMovieData(){
        if(sortBy == null){
            new MovieTask().execute(DEFAULT_SORTING);
        }
        else{
            new MovieTask().execute(sortBy);
        }
    }

    private void showErrorView(){
        mMovieRecylerView.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showSuccessView(){
        mMovieRecylerView.setVisibility(View.VISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }

    public class MovieTask extends AsyncTask<String, Void, List<Movie>> {

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
                List<Movie> movieData = JSONUtils.getMovies(MainActivity.this, jsonResponse);
                return movieData;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if(movies != null) {
                showSuccessView();
                mMoviesAdapter.setMovies(movies);
            }
            else{
                showErrorView();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.popular:
                sortBy = DEFAULT_SORTING;
                return true;
            case R.id.top_rated:
                sortBy = TOP_RATED;
                return true;
        }

        loadMovieData();

        return super.onOptionsItemSelected(item);
    }
}
