package com.example.musabir.apds.Mapper;

/**
 * Created by Musabir on 4/17/2018.
 */

public class EachLogMapper {
    private String date;
    private double value;

    public EachLogMapper(String date, double value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
