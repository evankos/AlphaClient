package com.startapp.vaggelis.alphaclient.structures;

import com.startapp.vaggelis.alphaclient.bootstrap.StaticContextApplication;

/**
 * Created by Vaggelis on 7/2/2017.
 */

public class GeoStructure {
    private Point point;
    public synchronized Point getPoint(){
        return point;
    }

    GeoStructure(){
        StaticContextApplication contextApplication = (StaticContextApplication)StaticContextApplication.getAppContext();
        this.point =  new Point(contextApplication.myLocationListener.getLat(),contextApplication.myLocationListener.getLong());
    }
}
