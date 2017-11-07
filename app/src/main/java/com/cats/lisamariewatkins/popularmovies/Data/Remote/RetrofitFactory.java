package com.cats.lisamariewatkins.popularmovies.Data.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lisa.watkins on 11/7/2017.
 */

public class RetrofitFactory {
    private static final String BASE_URL = "https://api.themoviedb.org";

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
