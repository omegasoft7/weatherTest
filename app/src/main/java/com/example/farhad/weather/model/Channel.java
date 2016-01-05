package com.example.farhad.weather.model;

import android.location.Location;

import lombok.Data;


@Data
public class Channel {

    private String title;
    private String link;
    private String description;
    private String language;
    private String lastBuildDate;
    private String ttl;
    private Location location;
    private Units units;
    private Item item;
}