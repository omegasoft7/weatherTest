package com.example.farhad.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Condition {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("temp")
    @Expose
    private String temp;
    @SerializedName("text")
    @Expose
    private String text;

}