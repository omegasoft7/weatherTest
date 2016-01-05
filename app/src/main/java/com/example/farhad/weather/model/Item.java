package com.example.farhad.weather.model;

import java.util.List;

import lombok.Data;

@Data
public class Item {

    private String title;
    private String lat;
    private String _long;
    private String link;
    private String pubDate;
    private Condition condition;
    private String description;
    private List<Forecast> forecast;
}