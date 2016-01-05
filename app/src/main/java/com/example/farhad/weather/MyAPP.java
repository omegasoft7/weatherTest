package com.example.farhad.weather;

import android.app.Application;

import com.example.farhad.weather.dagger.component.DaggerServiceComponent;
import com.example.farhad.weather.dagger.component.ServiceComponent;
import com.example.farhad.weather.dagger.module.ApplicationModule;

import fslogger.lizsoft.lv.fslogger.FSLogger;

/**
 * Created by farhad on 11/7/15.
 */
public class MyAPP extends Application {

    private ServiceComponent serviceComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();


        //initialize FSLogger
        FSLogger.init("Weather");
        FSLogger.setType(FSLogger.FSLoggerLimitationType.ALLOR);
        FSLogger.enableLoggingWithBackTrace();
        FSLogger.addCode(1);
        if (!BuildConfig.DEBUG) FSLogger.disable();
    }


    private void initDagger() {
        serviceComponent = DaggerServiceComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public ServiceComponent getServiceComponent() {
        return serviceComponent;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
