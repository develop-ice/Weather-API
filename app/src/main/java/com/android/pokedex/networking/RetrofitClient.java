package com.android.pokedex.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private static Retrofit retrofit;

    private RetrofitClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) mInstance = new RetrofitClient();
        return mInstance;
    }

    public ServiceApi getApi() {
        return retrofit.create(ServiceApi.class);
    }
}
