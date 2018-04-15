package com.example.musabir.apds.Mapper;

/**
 * Created by Musabir on 4/12/2018.
 */

public class CallAndSmsMapper {

    int type;
    double minValue;
    double maxValue;
    boolean enable;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

