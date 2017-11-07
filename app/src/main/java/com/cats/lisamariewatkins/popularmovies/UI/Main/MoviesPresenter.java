package com.cats.lisamariewatkins.popularmovies.UI.Main;

import android.support.annotation.NonNull;

import com.cats.lisamariewatkins.popularmovies.Data.DataManager;
import com.cats.lisamariewatkins.popularmovies.Data.Models.Movie;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lisa.watkins on 11/7/2017.
 */

public class MoviesPresenter implements MoviesContract.Presenter{
    private DataManager mDataManager;
    private final MoviesContract.View mMoviesView;
    private static final String DEFAULT_SORTING = "popular";
    private static final String TOP_RATED = "top_rated";
    private CompositeSubscription mSubscriptions;

    public MoviesPresenter(@NonNull MoviesContract.View moviesView){
        mMoviesView = moviesView;
        mDataManager = new DataManager();
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loadMovies(String sortPreference) {
        Subscription subscription;
        switch(sortPreference){
            case DEFAULT_SORTING:
                subscription = mDataManager.getPopularMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Movie>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mMoviesView.showError();
                        }

                        @Override
                        public void onNext(List<Movie> movies) {
                            if(!movies.isEmpty()) {
                                mMoviesView.showMovies(movies);
                            }
                        }
                    });

                mSubscriptions.add(subscription);
                break;
            case TOP_RATED:
                subscription = mDataManager.getTopRatedMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Movie>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mMoviesView.showError();
                        }

                        @Override
                        public void onNext(List<Movie> movies) {
                            if(!movies.isEmpty()) {
                                mMoviesView.showMovies(movies);
                            }
                        }
                    });


                break;
            default:
                subscription = mDataManager.getPopularMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Movie>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mMoviesView.showError();
                        }

                        @Override
                        public void onNext(List<Movie> movies) {
                            if(!movies.isEmpty()) {
                                mMoviesView.showMovies(movies);
                            }
                        }
                    });
                break;
        }
        mSubscriptions.add(subscription);
    }
}
