package com.startapp.vaggelis.alphaclient.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.startapp.vaggelis.alphaclient.R;
import com.startapp.vaggelis.alphaclient.bootstrap.StaticContextApplication;
import com.startapp.vaggelis.alphaclient.services.API;

/**
 * Created by Vaggelis on 6/28/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public API api;
    public StaticContextApplication staticContext;

//    public synchronized API getApi(){
//        return api;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ((StaticContextApplication)getApplicationContext()).getApi();
        staticContext = ((StaticContextApplication)getApplicationContext());
    }
}
