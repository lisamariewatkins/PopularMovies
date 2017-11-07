package com.cats.lisamariewatkins.popularmovies.UI.Main;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import com.cats.lisamariewatkins.popularmovies.Data.Models.Movie;
import com.cats.lisamariewatkins.popularmovies.R;
import com.cats.lisamariewatkins.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> mMovies;
    private final onMovieClickHandler mMovieClickHandler;

    public interface onMovieClickHandler{
        void onClick(Movie targetMovie);
    }

    public MoviesAdapter(onMovieClickHandler movieClickListener){
        mMovieClickHandler = movieClickListener;
    }

    public void setMovies(List<Movie> movies){
        mMovies = movies;
        notifyDataSetChanged();
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
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;
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
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie clickedMovie = mMovies.get(adapterPosition);
            mMovieClickHandler.onClick(clickedMovie);
        }
    }
}
