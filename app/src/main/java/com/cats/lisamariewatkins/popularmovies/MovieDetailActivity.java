package com.cats.lisamariewatkins.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cats.lisamariewatkins.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetailActivity extends AppCompatActivity {
    private String mTitle;
    private String mImageURL;
    private int mRating;
    private String mReleaseDate;
    private String mOverview;
    private TextView mTitleTV;
    private ImageView mPosterIV;
    private TextView mRatingTV;
    private TextView mReleaseDateTV;
    private TextView mOverviewTV;
    Intent intentThatStartedActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        intentThatStartedActivity = getIntent();

        setData();

        mTitleTV = (TextView) findViewById(R.id.title_tv);
        mPosterIV = (ImageView) findViewById(R.id.movie_poster_iv);
        mRatingTV = (TextView) findViewById(R.id.rating_tv);
        mReleaseDateTV = (TextView) findViewById(R.id.release_date_tv);
        mOverviewTV = (TextView) findViewById(R.id.overview_tv);

        passDataToViews();
    }

    private void setData(){
        mTitle = intentThatStartedActivity.getStringExtra("title");
        mImageURL = intentThatStartedActivity.getStringExtra("poster_path");
        mRating = intentThatStartedActivity.getIntExtra("rating", 0);
        mReleaseDate = intentThatStartedActivity.getStringExtra("release_date");
        mOverview = intentThatStartedActivity.getStringExtra("overview");
    }

    private void passDataToViews(){
        Uri moviePosterUrl = NetworkUtils.buildPosterUrl(mImageURL);
        Context context = this.mPosterIV.getContext();
        String releaseDate = getApplicationContext().getString(R.string.release_date, mReleaseDate);
        String rating = getApplicationContext().getString(R.string.rating, mRating);

        mTitleTV.setText(mTitle);
        Picasso.with(context).load(moviePosterUrl).into(mPosterIV);
        mRatingTV.setText(rating);
        mReleaseDateTV.setText(releaseDate);
        mOverviewTV.setText(mOverview);
    }

}
