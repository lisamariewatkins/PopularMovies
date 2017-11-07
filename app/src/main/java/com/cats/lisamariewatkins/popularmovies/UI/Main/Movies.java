package com.cats.lisamariewatkins.popularmovies.UI.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cats.lisamariewatkins.popularmovies.Data.DataManager;
import com.cats.lisamariewatkins.popularmovies.Data.Models.Movie;
import com.cats.lisamariewatkins.popularmovies.UI.MovieDetail.MovieDetailActivity;
import com.cats.lisamariewatkins.popularmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Movies extends AppCompatActivity implements MoviesContract.View, MoviesAdapter.onMovieClickHandler {
    public static final String POSTER_PATH = "poster_path";
    public static final String TITLE = "title";
    public static final String OVERVIEW = "overview";
    public static final String RATING = "rating";
    public static final String RELEASE_DATE = "release_date";
    @BindView(R.id.movies_rv) RecyclerView mMovieRecylerView;
    private MoviesAdapter mMoviesAdapter;
    @BindView(R.id.error_message) TextView mErrorTextView;
    @BindView(R.id.loading_progress) ProgressBar mLoadingProgressBar;
    private static final int NUMBER_OF_COLUMNS = 3;
    private static final String DEFAULT_SORTING = "popular";
    private static final String TOP_RATED = "top_rated";
    private String sortBy = DEFAULT_SORTING;
    private MoviesPresenter mMoviesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setUpRecyclerView();
        loadMovieData();
    }

    private void setUpRecyclerView(){
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mMovieRecylerView.setLayoutManager(layoutManager);
        mMovieRecylerView.setHasFixedSize(true);

        mMoviesPresenter = new MoviesPresenter(this);

        mMoviesAdapter = new MoviesAdapter(this);
        mMovieRecylerView.setAdapter(mMoviesAdapter);
    }

    @Override
    public void onClick(Movie targetMovie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Movie.MOVIE_INTENT, targetMovie);
        startActivity(intent);
    }

    private void loadMovieData(){
        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", MODE_PRIVATE);
        sortBy = pref.getString("SORT_BY", "popular");
        mMoviesPresenter.loadMovies(sortBy);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mMoviesAdapter.setMovies(movies);
        mMovieRecylerView.setVisibility(View.VISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showError(){
        mMovieRecylerView.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
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
