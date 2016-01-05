package com.example.farhad.weather.dagger.module;

import android.content.Context;

import com.example.farhad.weather.MyAPP;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private MyAPP myAPP;

    public ApplicationModule(MyAPP myAPP) {
        this.myAPP = myAPP;
    }

    @Provides
    public Context provivesContext() {
        return this.myAPP.getApplicationContext();
    }
}