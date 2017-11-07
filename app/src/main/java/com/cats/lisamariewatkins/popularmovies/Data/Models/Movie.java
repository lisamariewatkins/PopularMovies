package com.cats.lisamariewatkins.popularmovies.Data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public final class Movie implements Parcelable{
    public static final String MOVIE_INTENT = "Movie";
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private int userRating;
    @SerializedName("release_date")
    private String releaseDate;

    protected Movie(Parcel in){
        readFromParcel(in);
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
      public Movie createFromParcel(Parcel in){
          return new Movie(in);
      }
      public Movie[] newArray(int size){
          return new Movie[size];
      }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeInt(userRating);
        dest.writeString(releaseDate);
    }

    private void readFromParcel(Parcel in) {
        posterPath = in.readString();
        title = in.readString();
        overview = in.readString();
        userRating = in.readInt();
        releaseDate = in.readString();
    }
}
