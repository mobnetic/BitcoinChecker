package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class Coinome extends Market {

	private final static String NAME = "Coinome";
	private final static String TTS_NAME = "Coin ome";
	private final static String URL = "https://www.coinome.com/api/v1/ticker.json";

	public Coinome() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyPairId());
		ticker.bid = tickerJsonObject.getDouble("highest_bid");
		ticker.ask = tickerJsonObject.getDouble("lowest_ask");
		//ticker.vol = tickerJsonObject.getDouble("24hr_volume"); Currently null
		ticker.last = tickerJsonObject.getDouble("last");
	}

	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairArray = jsonObject.names();
		for(int i=0; i<pairArray.length(); ++i) {
			String pairId = pairArray.getString(i);
			String[] currencies = pairId.split("-");
			if(currencies.length >= 2){
				pairs.add(new CurrencyPairInfo(
						currencies[0].toUpperCase(Locale.US),
						currencies[1].toUpperCase(Locale.US),
						pairId
				));
			}
		}
	}
}

