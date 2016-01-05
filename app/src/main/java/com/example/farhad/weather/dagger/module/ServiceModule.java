package com.example.farhad.weather.dagger.module;

import com.example.farhad.weather.server.Observables;
import com.example.farhad.weather.server.api.ServerApi;
import com.example.farhad.weather.server.api.YahooServerApi;
import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module(
    includes = {ConfigModule.class, ApiModule.class}
)
public class ServiceModule {

    @Provides
    @Singleton
    Observables provideObservables(ServerApi api, YahooServerApi yahooServerApi, QNCache cache) {
        return new Observables(api, yahooServerApi, cache);
    }

    @Provides
    @Singleton
    QNCache provideQNCache() {
        return new QNCacheBuilder().setCaseSensitiveKeys(true).createQNCache();
    }
}