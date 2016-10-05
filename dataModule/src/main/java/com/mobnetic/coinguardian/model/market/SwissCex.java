package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class SwissCex extends Market {

	private final static String NAME = "SWISSCEX";
	private final static String TTS_NAME = "Swiss Cex";
	private final static String URL = "http://api.swisscex.com/quote/%1$s/%2$s?apiKey=%3$s";
	private final static String URL_CURRENCY_PAIRS = "http://api.swisscex.com/quotes?apiKey=%1$s";
	public static String API_KEY = "61u3kk4h2una357envden8cuk6";
	
	public SwissCex() {
		super(NAME, TTS_NAME, null);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter(), API_KEY);
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject quoteJsonObject = jsonObject.getJSONObject("quote");
		ticker.bid = quoteJsonObject.getDouble("bidPrice");
		ticker.ask = quoteJsonObject.getDouble("askPrice");
		ticker.vol = quoteJsonObject.getDouble("volume24");
		ticker.high = quoteJsonObject.getDouble("high24");
		ticker.low = quoteJsonObject.getDouble("low24");
		ticker.last = quoteJsonObject.getDouble("lastPrice");
	}
	
	@Override
	protected String parseError(int requestId, String responseString, CheckerInfo checkerInfo) throws Exception {
		if(TextUtils.isEmpty(API_KEY))
			return "API_KEY is empty";
		return responseString;
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return String.format(URL_CURRENCY_PAIRS, API_KEY);
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairNames = jsonObject.names();
		for(int i=0; i<pairNames.length(); ++i) {
			final JSONObject marketObject = jsonObject.getJSONObject(pairNames.getString(i));
			pairs.add(new CurrencyPairInfo(
					marketObject.getString("from"),
					marketObject.getString("to"),
					marketObject.getString("label")
				));
		}
	}
}
