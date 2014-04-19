package com.mobnetic.coinguardiandatamodule.tester.volley;

import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.RequestFuture;
import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardiandatamodule.tester.volley.CheckerVolleyMainRequest.TickerWithRawResponse;
import com.mobnetic.coinguardiandatamodule.tester.volley.generic.GenericCheckerVolleyRequest;

public class CheckerVolleyMainRequest extends GenericCheckerVolleyRequest<TickerWithRawResponse> {
	
	private final Market market;

	public CheckerVolleyMainRequest(Market market, CheckerInfo checkerInfo, Listener<TickerWithRawResponse> listener, ErrorListener errorListener) {
		super(market.getUrl(0, checkerInfo), checkerInfo, listener, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(5000, 3, 1.5f));
		this.market = market;
	}
	
	@Override
	protected TickerWithRawResponse parseNetworkResponse(String responseString) throws Exception {
		TickerWithRawResponse tickerWithRawResponse = new TickerWithRawResponse();
		tickerWithRawResponse.rawResponse = responseString;
		try {
			tickerWithRawResponse.ticker = market.parseTickerMain(0, responseString, new Ticker(), checkerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			tickerWithRawResponse.ticker = null;
		}
		
		if(tickerWithRawResponse.ticker==null || tickerWithRawResponse.ticker.last<=Ticker.NO_DATA) {
			String errorMsg;
			try {
				errorMsg = market.parseErrorMain(0, responseString, checkerInfo);
			} catch (Exception e) {
				errorMsg = null;
			}
			throw new CheckerErrorParsedError(responseString, errorMsg);
		}
		
		final int numOfRequests = market.getNumOfRequests(checkerInfo);
		if(numOfRequests>1) {
			for(int requestId=1; requestId<numOfRequests; ++requestId) {
				try {
					RequestFuture<String> future = RequestFuture.newFuture();
					final String nextUrl = market.getUrl(requestId, checkerInfo);
					if(!TextUtils.isEmpty(nextUrl)) {
						CheckerVolleyNextRequest request = new CheckerVolleyNextRequest(nextUrl, checkerInfo, future);
						getRequestQueue().add(request);
						String nextResponse = future.get(); // this will block
						market.parseTickerMain(requestId, nextResponse, tickerWithRawResponse.ticker, checkerInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return tickerWithRawResponse;
	}
	
	public class TickerWithRawResponse {
		
		public Ticker ticker;
		public String rawResponse;
		
	}
}
