package com.example.farhad.weather.server.api;

import com.example.farhad.weather.model.Weather;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface YahooServerApi {

    @GET("/v1/public/yql")
    Observable<Weather> getWeather(
            @Query("q") String q,
            @Query("format") String format
    );
}