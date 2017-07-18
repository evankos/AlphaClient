package com.startapp.vaggelis.alphaclient.bootstrap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import com.startapp.vaggelis.alphaclient.services.API;
import com.startapp.vaggelis.alphaclient.listeners.MyLocationListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Vaggelis on 6/28/2017.
 */

public class StaticContextApplication extends Application{
    private static Context context;
    private API api;
    private  Properties properties;
    public synchronized API getApi(){
        return api;
    }
    public LocationManager locationManager;
    public MyLocationListener myLocationListener;
    private Activity currentActivity = null;
    public Map<String,Object> settings = null;

    public Activity getCurrentActivity(){
        return currentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.currentActivity = mCurrentActivity;
    }

    public void onCreate() {
        super.onCreate();
        StaticContextApplication.context = getApplicationContext();
        properties = new Properties();
        try{
            InputStream is = context.getAssets().open("app.properties");
            properties.load(is);
        }catch (Exception e){
            e.printStackTrace();
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        api = new API((StaticContextApplication) StaticContextApplication.context);

        setUpSettings();
    }

    private void setUpSettings(){
        settings = new HashMap<>();
        settings.put("range", getProperty("default_range"));
    }

    public static Context getAppContext() {
        return StaticContextApplication.context;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
