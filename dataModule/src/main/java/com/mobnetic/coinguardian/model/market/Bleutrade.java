package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Bleutrade extends Market {
	
	private final static String NAME = "Bleutrade";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://bleutrade.com/api/v2/public/getticker?market=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://bleutrade.com/api/v2/public/getmarkets";
	
	public Bleutrade() {
		super(NAME, TTS_NAME, null);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final Object resultObject = jsonObject.get("result");
		JSONObject resultsJsonObject = null;
		if (resultObject instanceof JSONArray) {
			resultsJsonObject = ((JSONArray)resultObject).getJSONObject(0);
		} else {
			resultsJsonObject = (JSONObject)resultObject;
		}
		ticker.bid = resultsJsonObject.getDouble("Bid");
		ticker.ask = resultsJsonObject.getDouble("Ask");
		ticker.last = resultsJsonObject.getDouble("Last");
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("message");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray resultsJsonArray = jsonObject.getJSONArray("result");
		for (int i = 0; i < resultsJsonArray.length(); ++i) {
			final JSONObject pairJsonObject = resultsJsonArray.getJSONObject(i);
			final String pairId = pairJsonObject.getString("MarketName");
			final String currencyBase = pairJsonObject.getString("MarketCurrency");
			final String currencyCounter = pairJsonObject.getString("BaseCurrency");
			if (pairId != null && currencyBase != null && currencyCounter != null) {
				pairs.add(new CurrencyPairInfo(
						currencyBase,
						currencyCounter,
						pairId
					));
			}
		}
	}
}