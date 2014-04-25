package com.mobnetic.coinguardiandatamodule.tester.volley.generic;

import com.android.volley.NetworkResponse;
import com.android.volley.Response.Listener;

public abstract class ResponseListener<T> implements Listener<T> {
	
    public abstract void onResponse(NetworkResponse networkResponse, String responseString, T response);
    
    @Override
    public void onResponse(T response) {
    	onResponse(null, null, response);
    }
}
