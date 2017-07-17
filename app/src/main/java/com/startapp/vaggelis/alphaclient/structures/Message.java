package com.startapp.vaggelis.alphaclient.structures;

import org.json.JSONObject;
/**
 * Created by Vaggelis on 7/2/2017.
 */

public class Message extends GeoStructure implements JsonAble {
    private String message;
    public Message(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return String.format("{\"message\": \"%s\",  \"point\":\"%s\"}",message,getPoint().toString());
    }

    public JSONObject toJsonObject(){
        try {
            return new JSONObject(toString());
        }catch (Exception e){//TODO handle validation
            return new JSONObject();
        }
    }
}
