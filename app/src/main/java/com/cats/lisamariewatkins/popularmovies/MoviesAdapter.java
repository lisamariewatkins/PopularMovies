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

import com.cats.lisamariewatkins.popularmovies.Models.Movie;
import com.cats.lisamariewatkins.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by lisamwatkins on 8/30/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> mMovies;
    private onMovieClickHandler mMovieClickHandler;

    public interface onMovieClickHandler{
        public void onClick(Movie targetMovie);
    }

    public MoviesAdapter(onMovieClickHandler movieClickListener){
        mMovieClickHandler = movieClickListener;
    }

    public void setMovies(List<Movie> movies){
        mMovies = movies;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Movie currentMovie = mMovies.get(position);
        Uri moviePosterUrl = NetworkUtils.buildPosterUrl(currentMovie.getPosterPath());
        Context context = holder.mMoviePoster.getContext();

        Picasso.with(context).load(moviePosterUrl).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
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

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePoster;

        public MoviesViewHolder(View view){
            super(view);
            mMoviePoster = (ImageView) view.findViewById(R.id.iv_movie_poster);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie clickedMovie = mMovies.get(adapterPosition);
            mMovieClickHandler.onClick(clickedMovie);
        }
    }
}
