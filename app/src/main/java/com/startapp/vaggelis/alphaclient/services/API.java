package com.startapp.vaggelis.alphaclient.services;

import android.accounts.AccountManager;
import android.util.Log;


import com.startapp.vaggelis.alphaclient.bootstrap.StaticContextApplication;
import com.startapp.vaggelis.alphaclient.callables.NetworkCallable;
import com.startapp.vaggelis.alphaclient.listeners.NetworkListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vaggelis on 6/28/2017.
 */

public class API{

    public StaticContextApplication context;
    private Map<String,String> ApiBase = new HashMap<String, String>();
    private AccountManager am;
    public String token; //TODO make private
    private Requests requests;


    public API(StaticContextApplication staticContextApplication){
        super();
        context = staticContextApplication;
        am = AccountManager.get(context); //TODO use the account manager
        autoWireApi();
        requests = new Requests(context);
    }

    void autoWireApi(){
        //TODO add sitemap and autowire with request
        String base = String.format("%s://%s:%s/",context.getProperty("protocol"),
                context.getProperty("server"),
                context.getProperty("port"));
        ApiBase.put("auth",String.format("%s%s/", base, context.getProperty("api.auth")));
        ApiBase.put("users",String.format("%s%s/", base, context.getProperty("api.users")));
        ApiBase.put("groups",String.format("%s%s/", base, context.getProperty("api.groups")));
        ApiBase.put("posts",String.format("%s%s/", base, context.getProperty("api.posts")));
    }
    public void login(NetworkCallable networkCallable, Map params){


        JSONObject parameters = new JSONObject(params);
        String url = ApiBase.get("auth");



        NetworkListener networkListener = new NetworkListener<>(networkCallable);

        requests.postJsonObject(url,parameters, networkListener,token);
    }


    public void getGroups(NetworkCallable networkCallable){
        String url = ApiBase.get("groups");

        NetworkListener networkListener = new NetworkListener<>(networkCallable);

        requests.getJsonArray(url, null, networkListener,token);
    }

    public void refreshUserLocation(NetworkCallable networkCallable, JSONObject point){

    }

    public void getPosts(NetworkCallable networkCallable) throws JSONException{
        String url = ApiBase.get("posts");
        NetworkListener networkListener = new NetworkListener<>(networkCallable);
        requests.getJsonArray(url, null, networkListener,token);
    }

    public void sendPost (NetworkCallable networkCallable, JSONObject obj){

        String url = ApiBase.get("posts");

        NetworkListener networkListener = new NetworkListener<>(networkCallable);
        requests.postJsonObject(url,obj, networkListener,token);
    }
}




