package com.startapp.vaggelis.alphaclient.listeners;

import android.util.Log;

import com.startapp.vaggelis.alphaclient.bootstrap.StaticContextApplication;
import com.startapp.vaggelis.alphaclient.services.API;
import com.startapp.vaggelis.alphaclient.bootstrap.StaticContextApplication;
/**
 * Created by Vaggelis on 6/30/2017.
 */

public class BaseResponseListener  {
    API api;
    StaticContextApplication staticContextApplication;
    BaseResponseListener(){

        api = ((StaticContextApplication)StaticContextApplication.getAppContext()).getApi();
        staticContextApplication = ((StaticContextApplication)StaticContextApplication.getAppContext());

    }
}
