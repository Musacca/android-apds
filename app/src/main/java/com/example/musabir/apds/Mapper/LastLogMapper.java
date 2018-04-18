package com.example.musabir.apds.Mapper;

/**
 * Created by Musabir on 4/17/2018.
 */

public class LastLogMapper {


    String sensorId;
    String id;
    float  logValue;
    int logType;
    String logTypeName;

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getLogValue() {
        return logValue;
    }

    public void setLogValue(float logValue) {
        this.logValue = logValue;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getLogTypeName() {
        return logTypeName;
    }

    public void setLogTypeName(String logTypeName) {
        this.logTypeName = logTypeName;
    }
}
