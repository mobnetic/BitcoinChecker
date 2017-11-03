package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Abucoins extends Market {

	private final static String NAME = "Abucoins";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.abucoins.com/products/%1$s/stats";
	private final static String URL_CURRENCY_PAIRS = "https://api.abucoins.com/products";

	public Abucoins() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray jsonArray = new JSONArray(responseString);
		for(int i=0; i<jsonArray.length(); ++i) {
			JSONObject pairJsonObject = jsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					pairJsonObject.getString("base_currency"),
					pairJsonObject.getString("quote_currency"),
					pairJsonObject.getString("id")));
		}
	}
}
