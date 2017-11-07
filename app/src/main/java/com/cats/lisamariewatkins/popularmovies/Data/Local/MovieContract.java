package com.cats.lisamariewatkins.popularmovies.Data.Local;

import android.provider.BaseColumns;

/**
 * Created by lisa.watkins on 11/7/2017.
 */

public class MovieContract {
    public static final class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_POSTER_PATH = "poster_path";
    }
}
