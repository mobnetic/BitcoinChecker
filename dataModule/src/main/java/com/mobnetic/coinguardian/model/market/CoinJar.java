package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CoinJar extends Market {

	private final static String NAME = "CoinJar";
	private final static String TTS_NAME = "Coin Jar";
	private final static String URL = "https://api.coinjar.com/v3/exchange_rates";
	private final static String URL_CURRENCY_PAIRS = URL;

	public CoinJar() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject ratesJsonObject = jsonObject.getJSONObject("exchange_rates");
		final JSONObject pairJsonObject = ratesJsonObject.getJSONObject(checkerInfo.getCurrencyPairId());
		ticker.bid = pairJsonObject.getDouble("bid");
		ticker.ask = pairJsonObject.getDouble("ask");
		ticker.last = pairJsonObject.getDouble("midpoint");
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
		final JSONObject ratesJsonObject = jsonObject.getJSONObject("exchange_rates");
		final JSONArray namesJsonArray = ratesJsonObject.names();

		for(int i=0; i< namesJsonArray.length(); ++i) {
			final String symbol = namesJsonArray.getString(i);
			final JSONObject pairJsonObject = ratesJsonObject.getJSONObject(symbol);
			pairs.add(new CurrencyPairInfo(
					pairJsonObject.getString("base_currency"),
					pairJsonObject.getString("counter_currency"),
					symbol
			));
		}
	}
}
