package com.cats.lisamariewatkins.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.cats.lisamariewatkins.popularmovies.Models.Movie;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.onMovieClickHandler{
    public static final String POSTER_PATH = "poster_path";
    public static final String TITLE = "title";
    public static final String OVERVIEW = "overview";
    public static final String RATING = "rating";
    public static final String RELEASE_DATE = "release_date";
    private RecyclerView mMovieRecylerView;
    private MoviesAdapter mMoviesAdapter;
    private TextView mErrorTextView;
    private ProgressBar mLoadingProgressBar;
    private static final int NUMBER_OF_COLUMNS = 3;
    private static final String DEFAULT_SORTING = "popular";
    private static final String TOP_RATED = "top_rated";
    private String sortBy = DEFAULT_SORTING;
    private static final String PREFS_NAME = "MyPrefsFile";



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

        intent.putExtra(POSTER_PATH, targetMovie.getPosterPath());
        intent.putExtra(TITLE, targetMovie.getTitle());
        intent.putExtra(OVERVIEW, targetMovie.getOverview());
        intent.putExtra(RATING, targetMovie.getUserRating());
        intent.putExtra(RELEASE_DATE, targetMovie.getReleaseDate());

        startActivity(intent);
    }

    private void loadMovieData(){
        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", MODE_PRIVATE);
        sortBy = pref.getString("SORT_BY", "popular");
        new MovieTask(this, new MovieTaskListener(), mLoadingProgressBar).execute(sortBy);
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

    public class MovieTaskListener implements AsyncTaskCompleteListener<List<Movie>> {
        public void onTaskComplete(List<Movie> result){
            if(result != null) {
                showSuccessView();
                mMoviesAdapter.setMovies(result);
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
        SharedPreferences pref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        switch(id){
            case R.id.popular:
                sortBy = DEFAULT_SORTING;
                editor.putString("SORT_BY", sortBy);
                editor.apply();
                loadMovieData();
                return true;
            case R.id.top_rated:
                sortBy = TOP_RATED;
                editor.putString("SORT_BY", sortBy);
                editor.apply();
                loadMovieData();
                return true;
        }




        return super.onOptionsItemSelected(item);
    }
}
