package com.mobnetic.coinguardiandatamodule.tester.volley;

import com.android.volley.toolbox.RequestFuture;
import com.mobnetic.coinguardiandatamodule.tester.volley.generic.GzipVolleyRequest;

public class DynamicCurrencyPairsVolleyNextRequest extends GzipVolleyRequest<String> {

	public DynamicCurrencyPairsVolleyNextRequest(String url, RequestFuture<String> future) {
		super(url, future, future);
	}

	@Override
	protected String parseNetworkResponse(String responseString) throws Exception {
		return responseString;
	}

}
