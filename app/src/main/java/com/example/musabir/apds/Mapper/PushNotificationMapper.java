package com.example.musabir.apds.Mapper;

/**
 * Created by Musabir on 4/12/2018.
 */

public class PushNotificationMapper {

    int type;
    double minValueForCall;
    double maxValueForCall;
    double minValueForNotify;
    double maxValueForNotify;
    int callStatus;
    int notifyStatus;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMinValueForCall() {
        return minValueForCall;
    }

    public void setMinValueForCall(double minValueForCall) {
        this.minValueForCall = minValueForCall;
    }

    public double getMaxValueForCall() {
        return maxValueForCall;
    }

    public void setMaxValueForCall(double maxValueForCall) {
        this.maxValueForCall = maxValueForCall;
    }

    public double getMinValueForNotify() {
        return minValueForNotify;
    }

    public void setMinValueForNotify(double minValueForNotify) {
        this.minValueForNotify = minValueForNotify;
    }

    public double getMaxValueForNotify() {
        return maxValueForNotify;
    }

    public void setMaxValueForNotify(double maxValueForNotify) {
        this.maxValueForNotify = maxValueForNotify;
    }

    public int getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(int callStatus) {
        this.callStatus = callStatus;
    }

    public int getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(int notifyStatus) {
        this.notifyStatus = notifyStatus;
    }


    @Override
    public String toString() {
        return "PushNotificationMapper{" +
                "type=" + type +
                ", minValueForCall=" + minValueForCall +
                ", maxValueForCall=" + maxValueForCall +
                ", minValueForNotify=" + minValueForNotify +
                ", maxValueForNotify=" + maxValueForNotify +
                ", callStatus=" + callStatus +
                ", notifyStatus=" + notifyStatus +
                '}';
    }
}

