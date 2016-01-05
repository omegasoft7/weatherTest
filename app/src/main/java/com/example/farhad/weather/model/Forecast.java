package com.example.farhad.weather.model;

import lombok.Data;

@Data
public class Forecast {

    private String code;
    private String date;
    private String day;
    private String high;
    private String low;
    private String text;
}