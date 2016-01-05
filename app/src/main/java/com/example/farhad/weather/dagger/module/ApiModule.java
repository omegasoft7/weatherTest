package com.example.farhad.weather.dagger.module;

import com.example.farhad.weather.server.api.ServerApi;
import com.example.farhad.weather.server.api.YahooServerApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Singleton
@Module(
    includes = ConfigModule.class
)
public class ApiModule {

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder()
            .create();
    }

    @Singleton
    @Provides
    ServerApi provideServerApi(@DataFile RestAdapter adapter) {
        return adapter.create(ServerApi.class);
    }

    @Singleton
    @Provides
    YahooServerApi provideYahooServerApi(@Yahoo RestAdapter adapter) {
        return adapter.create(YahooServerApi.class);
    }

    @Singleton
    @Provides
    @DataFile
    RestAdapter provideDataFileRestAdapter(Gson gson, OkHttpClient okHttpClient) {
        return new RestAdapter.Builder()
                .setEndpoint("https://shopgate-static.s3.amazonaws.com/worktrail/backend/weather")
                .setClient(new OkClient(okHttpClient))
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Singleton
    @Provides
    @Yahoo
    RestAdapter provideYahooRestAdapter(Gson gson, OkHttpClient okHttpClient) {
        return new RestAdapter.Builder()
                .setEndpoint("https://query.yahooapis.com")
                .setClient(new OkClient(okHttpClient))
//                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient provideUserlessOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();

        return okHttpClient;
    }


    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Yahoo {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DataFile {
    }

}