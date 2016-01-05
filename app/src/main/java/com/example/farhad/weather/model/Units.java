package com.example.farhad.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Units {

    private String distance;
    private String pressure;
    private String speed;
    private String temperature;
}