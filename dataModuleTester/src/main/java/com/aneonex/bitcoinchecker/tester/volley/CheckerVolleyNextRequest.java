package com.aneonex.bitcoinchecker.tester.volley;

import java.util.Map;

import com.android.volley.toolbox.RequestFuture;
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo;
import com.aneonex.bitcoinchecker.tester.volley.generic.GenericCheckerVolleyRequest;

public class CheckerVolleyNextRequest extends GenericCheckerVolleyRequest<String> {
	
	public CheckerVolleyNextRequest(String url, CheckerInfo checkerInfo, RequestFuture<String> future) {
		super(url, checkerInfo, future, future);
	}

	@Override
	protected String parseNetworkResponse(Map<String, String> headers, String responseString) throws Exception {
		return responseString;
	}
}
