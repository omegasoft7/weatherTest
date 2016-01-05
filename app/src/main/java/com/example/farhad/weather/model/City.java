package com.example.farhad.weather.model;

import lombok.Data;

/**
 * Created by farhad on 11/7/15.
 */
@Data
public class City implements Comparable<City> {

    private String country;
    private String zip;
    private String city;
    private String longitude;
    private String latitude;
    private Channel channel;
    private float distance;

    @Override
    public int compareTo(City city) {
        return (int) (distance * 10 - city.distance * 10);
    }
}
