package com.cats.lisamariewatkins.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{
    private RecyclerView mMovieRecylerView;
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieRecylerView = (RecyclerView) findViewById(R.id.movies_rv);
        mMoviesAdapter = new MoviesAdapter();
        mMovieRecylerView.setAdapter(mMoviesAdapter);
    }

}
