package com.mobnetic.coinguardiandatamodule.tester.volley;

import java.util.Map;

import com.android.volley.toolbox.RequestFuture;
import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardiandatamodule.tester.volley.generic.GenericCheckerVolleyRequest;

public class CheckerVolleyNextRequest extends GenericCheckerVolleyRequest<String> {
	
	public CheckerVolleyNextRequest(String url, CheckerInfo checkerInfo, RequestFuture<String> future) {
		super(url, checkerInfo, future, future);
	}

	@Override
	protected String parseNetworkResponse(Map<String, String> headers, String responseString) throws Exception {
		return responseString;
	}
}
