package com.android.pokedex.networking;


import com.android.pokedex.model.OpenWeatherMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ServiceApi {

    @GET("weather?appid=e0e4d96e41aa0691fc64da22726a39d6&units=metric")
    Call<OpenWeatherMap> fetchWeaterWhithLocation(@Query("lat") double lat, @Query("lon") double lon);

    @GET("weather?appid=e0e4d96e41aa0691fc64da22726a39d6&units=metric")
    Call<OpenWeatherMap> fetchWeaterWhithCityName(@Query("q") String name);

}
