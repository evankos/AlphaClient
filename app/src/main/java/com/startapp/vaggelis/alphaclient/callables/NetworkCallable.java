package com.startapp.vaggelis.alphaclient.callables;

import com.android.volley.VolleyError;


/**
 * Created by Vaggelis on 6/28/2017.
 */

public interface NetworkCallable<V>{
    void before();
    void success(V response);
    void fail(VolleyError error);
}
