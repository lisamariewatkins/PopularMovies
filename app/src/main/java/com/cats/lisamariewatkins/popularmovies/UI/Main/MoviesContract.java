package com.cats.lisamariewatkins.popularmovies.UI.Main;

import com.cats.lisamariewatkins.popularmovies.UI.Base.BasePresenter;
import com.cats.lisamariewatkins.popularmovies.UI.Base.BaseView;

/**
 * Created by lisa.watkins on 11/7/2017.
 */

public class MoviesContract {
    interface View extends BaseView<Presenter>{
        void showMovies();
        void showProgress();
        void showError();
    }
    interface Presenter extends BasePresenter{
        void loadMovies(String sort);
    }
}
