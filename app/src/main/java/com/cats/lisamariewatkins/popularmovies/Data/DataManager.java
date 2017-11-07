package com.cats.lisamariewatkins.popularmovies.Data;

import com.cats.lisamariewatkins.popularmovies.BuildConfig;
import com.cats.lisamariewatkins.popularmovies.Data.Models.Movie;
import com.cats.lisamariewatkins.popularmovies.Data.Models.Review;
import com.cats.lisamariewatkins.popularmovies.Data.Models.Trailer;
import com.cats.lisamariewatkins.popularmovies.Data.Remote.MoviesDbApi;
import com.cats.lisamariewatkins.popularmovies.Data.Remote.RetrofitFactory;

import java.util.List;

import rx.Observable;

/**
 * Created by lisa.watkins on 11/7/2017.
 */

public class DataManager {
    private MoviesDbApi apiService;
    private static final String API_KEY = BuildConfig.API_KEY;

    public DataManager(){
        apiService = RetrofitFactory.getRetrofit().create(MoviesDbApi.class);
    }

    public Observable<List<Movie>> getPopularMovies(){
        return apiService.popularMovies(API_KEY);
    }

    public Observable<List<Movie>> getTopRatedMovies(){
        return apiService.topRatedMovies(API_KEY);
    }

    public Observable<Trailer> getTrailers(int id){
        return apiService.trailers(id);
    }

    public Observable<Review> getReviews(int id){
        return apiService.reviews(id);
    }
}
