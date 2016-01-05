package com.example.farhad.weather.dagger.component;

import com.example.farhad.weather.MainActivity;
import com.example.farhad.weather.dagger.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(MainActivity activity);

}