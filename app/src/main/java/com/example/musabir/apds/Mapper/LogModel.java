package com.example.musabir.apds.Mapper;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Musabir on 4/11/2018.
 */

public class LogModel{

    ArrayList<EachLogMapper> eachLogMappers;
    @PrimaryKey
    private String sensorId;
    private int type;

    public LogModel() {
    }

    public LogModel(ArrayList<EachLogMapper> eachLogMappers, String sensorId, int type) {
        this.eachLogMappers = eachLogMappers;
        this.sensorId = sensorId;
        this.type = type;
    }

    public ArrayList<EachLogMapper> getEachLogMappers() {
        return eachLogMappers;
    }

    public void setEachLogMappers(ArrayList<EachLogMapper> eachLogMappers) {
        this.eachLogMappers = eachLogMappers;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
