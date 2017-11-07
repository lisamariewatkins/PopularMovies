package com.cats.lisamariewatkins.popularmovies.UI.Main;

import android.support.annotation.NonNull;

import com.cats.lisamariewatkins.popularmovies.Data.DataManager;

/**
 * Created by lisa.watkins on 11/7/2017.
 */

public class MoviesPresenter implements MoviesContract.Presenter{
    private DataManager mDataManager;
    private final MoviesContract.View mMoviesView;
    private static final String DEFAULT_SORTING = "popular";
    private static final String TOP_RATED = "top_rated";

    public MoviesPresenter(@NonNull MoviesContract.View moviesView){
        mMoviesView = moviesView;
        mDataManager = new DataManager();
    }

    @Override
    public void loadMovies(String sortPreference) {
        switch(sortPreference){
            case DEFAULT_SORTING:
                mDataManager.getPopularMovies();
                break;
            case TOP_RATED:
                mDataManager.getTopRatedMovies();
                break;
            default:
                mDataManager.getPopularMovies();
        }
    }
}
