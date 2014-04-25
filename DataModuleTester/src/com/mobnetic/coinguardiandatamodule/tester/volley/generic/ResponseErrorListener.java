package com.mobnetic.coinguardiandatamodule.tester.volley.generic;

import com.android.volley.NetworkResponse;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

public abstract class ResponseErrorListener implements ErrorListener {

	public abstract void onErrorResponse(NetworkResponse networkResponse, String responseString, VolleyError error);
	
	@Override
	public void onErrorResponse(VolleyError error) {
		onErrorResponse(null, null, error);
	}

}
