package com.startapp.vaggelis.alphaclient.listeners;

import android.app.ProgressDialog;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.startapp.vaggelis.alphaclient.callables.NetworkCallable;

/**
 * Created by Vaggelis on 6/28/2017.
 */

public class NetworkListener<V> extends BaseResponseListener implements Response.Listener<V>, Response.ErrorListener {
    NetworkCallable<V> networkCallable;
    ProgressDialog pDialog;

    public NetworkListener(NetworkCallable<V> networkCallable){

        pDialog = new ProgressDialog(staticContextApplication.getCurrentActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        networkCallable.before();

        this.networkCallable = networkCallable;
    }

    @Override
    public void onResponse(V response) {//TODO replace this architecture with AOP when aspectj has an official lib
        pDialog.dismiss();
        networkCallable.success(response);
    }


    @Override
    public void onErrorResponse(VolleyError error) {//Can add extra code for validation here
        pDialog.dismiss();
        networkCallable.fail(error);
    }
}
