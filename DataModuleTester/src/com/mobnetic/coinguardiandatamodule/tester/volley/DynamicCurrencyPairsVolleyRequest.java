package com.mobnetic.coinguardiandatamodule.tester.volley;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.CurrencyPairsListWithDate;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.util.CurrencyPairsMapHelper;
import com.mobnetic.coinguardiandatamodule.tester.util.MarketCurrencyPairsStore;
import com.mobnetic.coinguardiandatamodule.tester.volley.generic.GzipVolleyRequest;

public class DynamicCurrencyPairsVolleyRequest extends GzipVolleyRequest<CurrencyPairsMapHelper> {

	private final Context context;
	private final Market market;
	
	public DynamicCurrencyPairsVolleyRequest(Context context, Market market, Listener<CurrencyPairsMapHelper> listener, ErrorListener errorListener) {
		super(market.getCurrencyPairsUrl(), listener, errorListener);
		
		this.context = context;
		this.market = market;
	}

	@Override
	protected CurrencyPairsMapHelper parseNetworkResponse(String responseString) throws Exception {
		List<CurrencyPairInfo> pairs = new ArrayList<CurrencyPairInfo>();
		market.parseCurrencyPairsMain(responseString, pairs);
		
		CurrencyPairsListWithDate currencyPairsListWithDate = new CurrencyPairsListWithDate();
		currencyPairsListWithDate.date = System.currentTimeMillis();
		currencyPairsListWithDate.pairs = pairs;
		
		if(pairs!=null && pairs.size()>0)
			MarketCurrencyPairsStore.savePairsForMarket(context, market.key, currencyPairsListWithDate);
		
		return new CurrencyPairsMapHelper(currencyPairsListWithDate);
	}
}
