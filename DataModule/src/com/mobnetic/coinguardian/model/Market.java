package com.mobnetic.coinguardian.model;

import java.util.HashMap;

import org.json.JSONObject;

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
	
	public final Ticker parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		parseTickerInner(requestId, responseString, ticker, checkerInfo);
		if(ticker.timestamp<=0)
			ticker.timestamp = System.currentTimeMillis();
		else
			ticker.timestamp = TimeUtils.parseTimeToMillis(ticker.timestamp);
		
		return ticker;
	}
	
	// ====================
	// Parse Ticker Inner
	// ====================
	protected void parseTickerInner(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		parseTickerInnerFromJsonObject(requestId, new JSONObject(responseString), ticker, checkerInfo);
	}
	protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		// do parsing
	}
	
	// ====================
	// Parse Error
	// ====================
	public String parseError(int requestId, String responseString, CheckerInfo checkerInfo) throws Exception {
		return parseErrorFromJsonObject(requestId, new JSONObject(responseString), checkerInfo);
	}
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		throw new Exception();
	}
}
