package com.mobnetic.coinguardian.model.market;

import org.json.JSONObject;
import org.json.JSONArray;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import java.util.List;

public class SafeTrade extends Market {

	private final static String NAME = "SafeTrade";
	private final static String TTS_NAME = NAME; 
	private final static String URL = "https://safe.trade/api/v2/tickers/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://safe.trade/api/v2/markets/";
	
	public SafeTrade() {
		super(NAME, TTS_NAME, null);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject jsonTicker = jsonObject.getJSONObject("ticker");

		ticker.bid = jsonTicker.getDouble("buy");
		ticker.ask = jsonTicker.getDouble("sell");
		ticker.low = jsonTicker.getDouble("low");
		ticker.high = jsonTicker.getDouble("high");
		ticker.last = jsonTicker.getDouble("last");
		ticker.vol = jsonTicker.getDouble("vol");
		ticker.timestamp = jsonObject.getLong("at");
	}

	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray data = new JSONArray(responseString);
		for(int i = 0; i < data.length(); ++i) {
			final JSONObject pairJsonObject = data.getJSONObject(i);
			String[] name = pairJsonObject.getString("name").split("/");

			pairs.add(new CurrencyPairInfo(
					name[0],
					name[1],
					pairJsonObject.getString("id")
			));
		}
	}
}
