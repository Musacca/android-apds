package com.example.musabir.apds.Mapper;

/**
 * Created by Musabir on 3/2/2018.
 */

public class FilterMapper {
    float value;

    String time;
    String date;
    int type;

    public FilterMapper(int value, String time, String date, int type) {
        this.value = value;
        this.time = time;
        this.date = date;
        this.type = type;
    }
    public  FilterMapper(){}

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
