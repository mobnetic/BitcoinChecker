package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Koinex extends Market {

	private final static String NAME = "Koinex";
	private final static String TTS_NAME = "Koin ex";
	private final static String URL = "https://koinex.in/api/ticker";
	private final static String URL_CURRENCY_PAIRS = URL;

	public Koinex() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject names = jsonObject.getJSONObject("stats");
		JSONObject tickerJsonObject = names.getJSONObject(checkerInfo.getCurrencyBase());
		ticker.bid = tickerJsonObject.getDouble("highest_bid");
		ticker.ask = tickerJsonObject.getDouble("lowest_ask");
		ticker.vol = tickerJsonObject.getDouble("vol_24hrs");
		ticker.high = tickerJsonObject.getDouble("max_24hrs");
		ticker.low = tickerJsonObject.getDouble("min_24hrs");
		ticker.last = tickerJsonObject.getDouble("last_traded_price");
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
		final JSONObject currencyJSONObject = jsonObject.getJSONObject("stats");
		final JSONArray currencyArray = currencyJSONObject.names();
		for(int i=0; i<currencyArray.length(); ++i) {
			pairs.add(new CurrencyPairInfo(currencyArray.getString(i), Currency.INR, null));
		}
	}
}

