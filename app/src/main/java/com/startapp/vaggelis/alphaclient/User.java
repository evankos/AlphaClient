package com.startapp.vaggelis.alphaclient;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.startapp.vaggelis.alphaclient.activities.BaseActivity;
import com.startapp.vaggelis.alphaclient.bootstrap.StaticContextApplication;
import com.startapp.vaggelis.alphaclient.callables.NetworkCallable;
import com.startapp.vaggelis.alphaclient.listeners.MyLocationListener;
import com.startapp.vaggelis.alphaclient.structures.Message;
import com.startapp.vaggelis.alphaclient.structures.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class User extends BaseActivity implements OnMapReadyCallback {

    private static Button refresh_button;
    private static Button send_button;
    private static EditText message;
    private static ListView listview;
    private static MapView mapView;
    private static GoogleMap googleMap;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<Point> locationList = new ArrayList<Point>();
    private StableArrayAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticContext.setCurrentActivity(this);
        setContentView(R.layout.activity_user);
        staticContext.myLocationListener= new MyLocationListener(((StaticContextApplication)getApplicationContext()).locationManager,this);
        AutoWire();
        setUpMap(savedInstanceState);
    }


    private void setUpMap(Bundle savedInstanceState){
        mapView = (MapView) findViewById(R.id.map);
        if (mapView != null) {
            Log.d(toString(),"Map Exists");
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        Log.d(toString(),"Map has been created");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ){
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION}, 102
            );
        }

        zoomToLocation(staticContext.myLocationListener.getLat(),staticContext.myLocationListener.getLong(), 8);
    }

    private void zoomToLocation(double lat,double lon, float ZoomFactor){
        LatLng latLng = new LatLng(lat,lon);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZoomFactor));
    }


    private void AutoWire(){
        message = (EditText)findViewById(R.id.message);
        refresh_button = (Button)findViewById(R.id.refresh);
        send_button = (Button)findViewById(R.id.send);
        listview = (ListView) findViewById(R.id.posts);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                final String message = (String) parent.getItemAtPosition(position);
                final Point itemPoint = locationList.get(position);


                Log.d(toString(),String.format("Clicked a list message %d  %s",position,itemPoint.toString()));
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(itemPoint.GetLat(), itemPoint.GetLong()))
                        .title(message));
                zoomToLocation(itemPoint.GetLat(),itemPoint.GetLong(),12);
//                view.animate().setDuration(2000).alpha(0)
//                        .withEndAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                list.remove(message);
//                                adapter.notifyDataSetChanged();
//                                view.setAlpha(1);
//                            }
//                        });
            }

        });





        send_button.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{

                        String data = message.getText().toString();
                        if(data.length()<5){
                            //Message Too short
                        }
                        else{

                            JSONObject jsonBody = new Message(data).toJsonObject();

                            NetworkCallable<JSONArray> loginHandle = new NetworkCallable<JSONArray>() {
                                @Override
                                public void before() {
                                }

                                @Override
                                public void success(JSONArray response) {

                                }

                                @Override
                                public void fail(VolleyError error) {
                                    Log.d(this.getClass().getName(), error.getMessage());
                                    message.getText().clear();
                                }
                            };

                            api.sendPost(loginHandle,jsonBody);
                        }
                    }catch (Exception e){
                        e.printStackTrace();//TODO add some good exception handling
                    }
                }
            }
        );


        refresh_button.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NetworkCallable<JSONArray> loginHandle = new NetworkCallable<JSONArray>() {
                        @Override
                        public void before() {

                        }
                        @Override
                        public void success(JSONArray response) {
                            ArrayList<String> list = new ArrayList<String>();
                            locationList.clear();
                            try{
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject row = response.getJSONObject(i);
                                    list.add(row.getString("message"));
                                    locationList.add(new Point((JSONArray)row.get("point")));
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            adapter = new StableArrayAdapter(User.this,
                                    android.R.layout.simple_list_item_1, list);
                            listview.setAdapter(adapter);
                        }

                        @Override
                        public void fail(VolleyError error) {
                            Log.d(toString(), "Network error: "+error.getMessage());
                        }
                    };
                    try{
                        api.getPosts(loginHandle);
                    }catch (JSONException e){
                        Log.d(toString(),"Exception in GetPosts");//TODO centralize exception handling
                    }

                }
            }
        );
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
