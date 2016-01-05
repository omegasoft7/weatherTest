package com.example.farhad.weather.dagger.module;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module(
    includes = ApplicationModule.class
)
public class ConfigModule {
    @Provides
    @Singleton
    Properties provideApplicationProperties(Context context) {
        AssetManager assetManager = context.getAssets();
        Properties p = new Properties();
        try {
            p.load(assetManager.open("app.properties"));
            return p;
        } catch (IOException e) {
            throw new RuntimeException("Properties file not found");
        }
    }
}