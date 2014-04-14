package com.mobnetic.coinguardiandatamodule.tester.volley;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.RequestFuture;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.CurrencyPairsListWithDate;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.util.CurrencyPairsMapHelper;
import com.mobnetic.coinguardiandatamodule.tester.util.MarketCurrencyPairsStore;
import com.mobnetic.coinguardiandatamodule.tester.volley.generic.GzipVolleyRequest;

public class DynamicCurrencyPairsVolleyMainRequest extends GzipVolleyRequest<CurrencyPairsMapHelper> {

	private final Context context;
	private final Market market;
	private RequestQueue requestQueue;
	
	public DynamicCurrencyPairsVolleyMainRequest(Context context, Market market, Listener<CurrencyPairsMapHelper> listener, ErrorListener errorListener) {
		super(market.getCurrencyPairsUrl(0), listener, errorListener);
		
		
		this.context = context;
		this.market = market;
	}

	@Override
	public Request<?> setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
		return super.setRequestQueue(requestQueue);
	}
	
	@Override
	protected CurrencyPairsMapHelper parseNetworkResponse(String responseString) throws Exception {
		List<CurrencyPairInfo> pairs = new ArrayList<CurrencyPairInfo>();
		market.parseCurrencyPairsMain(0, responseString, pairs);
			
		final int numOfRequests = market.getCurrencyPairsNumOfRequests();
		if(numOfRequests>1) {
			for(int requestId=1; requestId<numOfRequests; ++requestId) {
				try {
					RequestFuture<String> future = RequestFuture.newFuture();
					final String nextUrl = market.getCurrencyPairsUrl(requestId);
					if(!TextUtils.isEmpty(nextUrl)) {
						DynamicCurrencyPairsVolleyNextRequest request = new DynamicCurrencyPairsVolleyNextRequest(nextUrl, future);
						requestQueue.add(request);
						String nextResponse = future.get(); // this will block
						market.parseCurrencyPairsMain(requestId, nextResponse, pairs);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		CurrencyPairsListWithDate currencyPairsListWithDate = new CurrencyPairsListWithDate();
		currencyPairsListWithDate.date = System.currentTimeMillis();
		currencyPairsListWithDate.pairs = pairs;
		
		if(pairs!=null && pairs.size()>0)
			MarketCurrencyPairsStore.savePairsForMarket(context, market.key, currencyPairsListWithDate);
		
		return new CurrencyPairsMapHelper(currencyPairsListWithDate);
	}
}
