package com.cats.lisamariewatkins.popularmovies.Data.Remote;

import com.cats.lisamariewatkins.popularmovies.Data.Models.Movie;
import com.cats.lisamariewatkins.popularmovies.Data.Models.Review;
import com.cats.lisamariewatkins.popularmovies.Data.Models.Trailer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import rx.Observable;

/**
 * Created by lisa.watkins on 11/7/2017.
 */

public interface MoviesDbApi {
    @GET("3/movie/top_rated")
    Observable<List<Movie>> topRatedMovies(@Query("api_key") String apiKey);

    @GET("3/movie/popular")
    Observable<List<Movie>> popularMovies(@Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Observable<Trailer> trailers(@Path("id") int movieId);

    @GET("3/movie/{id}/reviews")
    Observable<Review> reviews(@Path("id") int movieId);
}
