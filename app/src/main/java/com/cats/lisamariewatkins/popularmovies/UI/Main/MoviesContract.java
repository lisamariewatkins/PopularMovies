package com.cats.lisamariewatkins.popularmovies.UI.Main;

import com.cats.lisamariewatkins.popularmovies.Data.Models.Movie;

import java.util.List;

/**
 * Created by lisa.watkins on 11/7/2017.
 */

public class MoviesContract {
    interface View {
        void showMovies(List<Movie> movies);
        void showProgress();
        void showError();
    }
    interface Presenter {
        void loadMovies(String sort);
    }
}
