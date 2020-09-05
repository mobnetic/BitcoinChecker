package com.aneonex.bitcoinchecker.tester.volley.generic;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo;

public abstract class GenericCheckerVolleyRequest<T> extends GzipVolleyRequest<T> {
	
	protected final CheckerInfo checkerInfo;

	public GenericCheckerVolleyRequest(String url, CheckerInfo checkerInfo, Listener<T> listener, ErrorListener errorListener) {
		super(url, listener, errorListener);
		
		this.checkerInfo = checkerInfo;
	}
}
