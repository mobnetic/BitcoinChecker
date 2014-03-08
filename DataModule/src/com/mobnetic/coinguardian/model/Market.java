package com.mobnetic.coinguardian.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.text.TextUtils;

import com.mobnetic.coinguardian.util.TimeUtils;

public abstract class Market {

	public final String key;
	public final String name;
	public final String ttsName;
	public HashMap<String, CharSequence[]> currencyPairs;
	
	public Market(String name, String ttsName, HashMap<String, CharSequence[]> currencyPairs) {
		this.key = this.getClass().getSimpleName();
		this.name = name;
		this.ttsName = ttsName;
		this.currencyPairs = currencyPairs;
	}
	
	public int getCautionResId() {
		return 0;
	}
	
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 1;
	}
	
	public abstract String getUrl(int requestId, CheckerInfo checkerInfo);
	
	public final Ticker parseTickerMain(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		parseTicker(requestId, responseString, ticker, checkerInfo);
		if(ticker.timestamp<=0)
			ticker.timestamp = System.currentTimeMillis();
		else
			ticker.timestamp = TimeUtils.parseTimeToMillis(ticker.timestamp);
		
		return ticker;
	}
	
	public final String parseErrorMain(int requestId, String responseString, CheckerInfo checkerInfo) throws Exception {
		return parseError(requestId, responseString, checkerInfo);
	}

	public String getCurrencyPairsUrl() {
		return null;
	}
	
	public final void parseCurrencyPairsMain(String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		parseCurrencyPairs(responseString, pairs);
		
		for(int i=pairs.size()-1; i>=0; --i) {
			CurrencyPairInfo currencyPairInfo = pairs.get(i);
			if(currencyPairInfo==null || TextUtils.isEmpty(currencyPairInfo.getCurrencyBase()) || TextUtils.isEmpty(currencyPairInfo.getCurrencyCounter()))
				pairs.remove(i);
		}
		Collections.sort(pairs);
	}
	
	// ====================
	// Parse Ticker Inner
	// ====================
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		parseTickerFromJsonObject(requestId, new JSONObject(responseString), ticker, checkerInfo);
	}
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		// do parsing
	}
	
	// ====================
	// Parse Error
	// ====================
	protected String parseError(int requestId, String responseString, CheckerInfo checkerInfo) throws Exception {
		return parseErrorFromJsonObject(requestId, new JSONObject(responseString), checkerInfo);
	}
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		throw new Exception();
	}
	
	// ====================
	// Parse currency pairs
	// ====================
	protected void parseCurrencyPairs(String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		parseCurrencyPairsFromJsonObject(new JSONObject(responseString), pairs);
	}
	
	protected void parseCurrencyPairsFromJsonObject(JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		// do parsing
	}
}
