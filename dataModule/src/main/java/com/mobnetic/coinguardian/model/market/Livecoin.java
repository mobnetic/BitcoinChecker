package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Livecoin extends Market {

	private final static String NAME = "Livecoin";
	private final static String TTS_NAME = "Live coin";
	private final static String URL = "https://api.livecoin.net/exchange/ticker?currencyPair=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.livecoin.net/exchange/ticker";

	public Livecoin() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		if (!jsonObject.isNull("best_bid")) {
			ticker.bid = jsonObject.getDouble("best_bid");
		}
		if (!jsonObject.isNull("best_ask")) {
			ticker.ask = jsonObject.getDouble("best_ask");
		}
		if (!jsonObject.isNull("volume")) {
			ticker.vol = jsonObject.getDouble("volume");
		}
		if (!jsonObject.isNull("high")) {
			ticker.high = jsonObject.getDouble("high");
		}
		if (!jsonObject.isNull("low")) {
			ticker.low = jsonObject.getDouble("low");
		}
		ticker.last = jsonObject.getDouble("last");
	}

	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray tickerArray = new JSONArray(responseString);

		for(int i = 0; i < tickerArray.length(); ++i) {
			final JSONObject tickerRow = tickerArray.getJSONObject(i);
			final String symbol = tickerRow.getString("symbol");
			final String[] currencyNames = symbol.split("/");
			
			if(currencyNames != null && currencyNames.length >= 2) {
				pairs.add(new CurrencyPairInfo(
						currencyNames[0],
						currencyNames[1],
						symbol));
			}
		}
	}
}
