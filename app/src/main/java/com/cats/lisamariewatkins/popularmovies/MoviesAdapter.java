package com.cats.lisamariewatkins.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * Created by lisamwatkins on 8/30/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private String[] mMovies;

    public MoviesAdapter(){}

    public void setMovies(String[] movies){
        mMovies = movies;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        String currentMovie = mMovies[position];
        Uri currentMovieUri = Uri.parse(currentMovie);
        Context context = holder.mMoviePoster.getContext();

        Picasso.with(context).load(currentMovieUri).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.length;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediatelyToParent = false;

        View movieItemView = inflater.inflate(layoutId, parent, attachImmediatelyToParent);
        return new MoviesViewHolder(movieItemView);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mMoviePoster;

        public MoviesViewHolder(View view){
            super(view);
            mMoviePoster = (ImageView) view.findViewById(R.id.iv_movie_poster);
        }
    }
}
