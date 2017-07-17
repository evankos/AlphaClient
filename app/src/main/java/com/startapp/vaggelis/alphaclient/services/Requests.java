package com.startapp.vaggelis.alphaclient.services;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.startapp.vaggelis.alphaclient.bootstrap.StaticContextApplication;


import org.json.JSONArray;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Vaggelis on 6/29/2017.
 */

public class Requests {

    private RequestQueue mRequestQueue;

    Requests(StaticContextApplication context){
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public synchronized void postJsonObject(String url, JSONObject parameters, Response.Listener<JSONObject> listener, String token){
        String tag_json_obj = "json_obj_req";
        com.startapp.vaggelis.alphaclient.services.JsonObjectRequest jsonObjReq = new com.startapp.vaggelis.alphaclient.services.JsonObjectRequest(
                Request.Method.POST, url, parameters, listener, (Response.ErrorListener) listener, token);
        addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public synchronized void getJsonObject(String url,JSONObject parameters, Response.Listener<JSONObject> listener, String token){
        String tag_json_obj = "json_obj_req";
        com.startapp.vaggelis.alphaclient.services.JsonObjectRequest jsonObjReq = new com.startapp.vaggelis.alphaclient.services.JsonObjectRequest(
                Request.Method.GET, url, parameters, listener, (Response.ErrorListener) listener, token);
        addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public synchronized void getJsonArray(String url, JSONArray parameters, Response.Listener<JSONArray> listener, String token){
        String tag_json_obj = "json_obj_req";
        com.startapp.vaggelis.alphaclient.services.JsonArrayRequest jsonObjReq = new com.startapp.vaggelis.alphaclient.services.JsonArrayRequest(
                Request.Method.GET, url, parameters, listener, (Response.ErrorListener) listener, token);
        addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public synchronized void postJsonArray(String url,JSONArray parameters, Response.Listener<JSONArray> listener, String token){
        String tag_json_obj = "json_obj_req";
        com.startapp.vaggelis.alphaclient.services.JsonArrayRequest jsonObjReq = new com.startapp.vaggelis.alphaclient.services.JsonArrayRequest(
                Request.Method.POST, url, parameters, listener, (Response.ErrorListener) listener, token);
        addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public RequestQueue getRequestQueue() {

        return mRequestQueue;
    }



    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
