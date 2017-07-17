package com.startapp.vaggelis.alphaclient.services;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import org.json.JSONArray;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vaggelis on 6/28/2017.
 */

public class JsonArrayRequest extends com.android.volley.toolbox.JsonArrayRequest {
    private String token;
    JsonArrayRequest(int method, String url, JSONArray jsonRequest,
                     Response.Listener<JSONArray> listener, Response.ErrorListener errorListener,String token){
        super(method,url,jsonRequest,listener,errorListener);
        this.token = token;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params =  super.getHeaders();
        if(params==null || params == Collections.EMPTY_MAP)params = new HashMap<>();
        if(token != null)params.put("Authorization",String.format("Token %s",token));
        return params;
    }
}
