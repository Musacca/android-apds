package com.example.musabir.apds.Mapper;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Musabir on 4/11/2018.
 */

public class LogModel extends RealmObject {

    private String date;
    private double value;
    @PrimaryKey
    private String sensorId;
    private int type;

}
