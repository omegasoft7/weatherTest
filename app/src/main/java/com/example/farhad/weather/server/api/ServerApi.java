package com.example.farhad.weather.server.api;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Streaming;
import rx.Observable;

public interface ServerApi {

    @GET("/cities.dat")
    @Streaming
    Observable<Response> getDataFile();
}