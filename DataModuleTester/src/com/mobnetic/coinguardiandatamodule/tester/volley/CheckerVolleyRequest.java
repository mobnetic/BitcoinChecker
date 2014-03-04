package com.mobnetic.coinguardiandatamodule.tester.volley;

import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.RequestFuture;
import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class CheckerVolleyRequest extends GenericCheckerVolleyRequest<Ticker> {
	
	private final Market market;
	private RequestQueue requestQueue;

	public CheckerVolleyRequest(Market market, CheckerInfo checkerInfo, Listener<Ticker> listener, ErrorListener errorListener) {
		super(market.getUrl(0, checkerInfo), checkerInfo, listener, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(5000, 3, 1.5f));
		this.market = market;
	}
	
	@Override
	public void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
		super.setRequestQueue(requestQueue);
	}

	@Override
	protected Ticker parseNetworkResponse(String responseString) throws Exception {
		Ticker ticker;
		try {
			ticker = market.parseTickerMain(0, responseString, new Ticker(), checkerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			ticker = null;
		}
		
		if(ticker==null || ticker.last<=Ticker.NO_DATA) {
			throw new CheckerErrorParsedError(market.parseErrorMain(0, responseString, checkerInfo));
		}
		
		final int numOfRequests = market.getNumOfRequests(checkerInfo);
		if(numOfRequests>1) {
			for(int requestId=1; requestId<numOfRequests; ++requestId) {
				try {
					RequestFuture<String> future = RequestFuture.newFuture();
					final String nextUrl = market.getUrl(requestId, checkerInfo);
					if(!TextUtils.isEmpty(nextUrl)) {
						CheckerVolleyNextRequest request = new CheckerVolleyNextRequest(nextUrl, checkerInfo, future);
						requestQueue.add(request);
						String nextResponse = future.get(); // this will block
						market.parseTickerMain(requestId, nextResponse, ticker, checkerInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ticker;
	}
}
