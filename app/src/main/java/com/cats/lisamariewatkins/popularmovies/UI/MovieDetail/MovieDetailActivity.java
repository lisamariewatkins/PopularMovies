package com.cats.lisamariewatkins.popularmovies.UI.MovieDetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cats.lisamariewatkins.popularmovies.R;
import com.cats.lisamariewatkins.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MovieDetailActivity extends AppCompatActivity {
    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String RATING = "rating";
    public static final String RELEASE_DATE = "release_date";
    public static final String OVERVIEW = "overview";
    private String mTitle;
    private String mImageURL;
    private int mRating;
    private String mReleaseDate;
    private String mOverview;
    private TextView mTitleTV;
    private ImageView mPosterIV;
    private TextView mDetailsTV;
    private TextView mOverviewTV;
    private Intent intentThatStartedActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        intentThatStartedActivity = getIntent();

        setData();

        mTitleTV = (TextView) findViewById(R.id.title_tv);
        mPosterIV = (ImageView) findViewById(R.id.movie_poster_iv);
        mDetailsTV = (TextView) findViewById(R.id.details_tv);

        mOverviewTV = (TextView) findViewById(R.id.overview_tv);

        passDataToViews();
    }


    private void setData(){
        mTitle = intentThatStartedActivity.getStringExtra(TITLE);
        mImageURL = intentThatStartedActivity.getStringExtra(POSTER_PATH);
        mRating = intentThatStartedActivity.getIntExtra(RATING, 0);
        mReleaseDate = intentThatStartedActivity.getStringExtra(RELEASE_DATE);
        mOverview = intentThatStartedActivity.getStringExtra(OVERVIEW);
    }

    private void passDataToViews(){
        Uri moviePosterUrl = NetworkUtils.buildPosterUrl(mImageURL);
        Context context = this.mPosterIV.getContext();
        String releaseDate = getApplicationContext().getString(R.string.release_date, formatDate(mReleaseDate));
        String rating = getApplicationContext().getString(R.string.rating, mRating);

        mTitleTV.setText(mTitle);
        Picasso.with(context).load(moviePosterUrl).into(mPosterIV);
        mDetailsTV.append(rating + "\n");
        mDetailsTV.append(releaseDate);
        mOverviewTV.setText(mOverview);
    }

    private String formatDate(String date){
        try{
            SimpleDateFormat GIVEN_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");

            Date parsedDate = GIVEN_FORMAT.parse(date);
            return DATE_FORMAT.format(parsedDate);

        }
        catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }

}
