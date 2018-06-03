package com.example.yarde.light_p.Light_program;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yarde on 03/06/2018.
 */
@IgnoreExtraProperties
public class Data {

    private float lightSensor;
    private double longitude;
    private double altitude;

    public Data(){}

    public Data(float _lightSensor,double _longitude,double _altitude) {
        this.lightSensor = _lightSensor;
        this.longitude =_longitude;
        this.altitude = _altitude;
    }

    public Data(float lightSensor) {
        this.lightSensor = lightSensor;
        this.longitude =0;
        this.altitude = 0;

    }

    @Override
    public String toString() {
        return "Data{" +
                "lightSensor=" + lightSensor +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                '}';
    }

    public float getLightSensor() {
        return lightSensor;
    }

    public void setLightSensor(float lightSensor) {
        this.lightSensor = lightSensor;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}

