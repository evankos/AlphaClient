package com.startapp.vaggelis.alphaclient.structures;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Vaggelis on 7/2/2017.
 */

public class Point {
    private double lat;
    private double lon;

    public double GetLat(){
        return lat;
    }

    public double GetLong(){
        return lon;
    }

    public Point(JSONArray data) throws JSONException{//TODO cetralize exception handling
        this.lat = data.getDouble(1);
        this.lon = data.getDouble(0);
    }

    public Point(ArrayList<Double> data){
        this.lat = data.get(0);
        this.lon = data.get(1);
    }

    public Point(double lat,double lon){
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString(){
        return String.format("[%f,%f]",lon,lat);
    }

}
