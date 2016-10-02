package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class LakeBTC extends Market {

	private final static String NAME = "LakeBTC";
	private final static String TTS_NAME = "Lake BTC";
	private final static String URL = "https://www.lakebtc.com/api_v1/ticker";
	private final static String URL_CURRENCY_PAIRS = URL;
	
	public LakeBTC() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject pairJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyCounter());
		
		ticker.bid = pairJsonObject.getDouble("bid");
		ticker.ask = pairJsonObject.getDouble("ask");
		ticker.vol = pairJsonObject.getDouble("volume");
		ticker.high = pairJsonObject.getDouble("high");
		ticker.low = pairJsonObject.getDouble("low");
		ticker.last = pairJsonObject.getDouble("last");
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
		final JSONArray currencyCounterJsonArray = jsonObject.names();
		for(int i=0; i<currencyCounterJsonArray.length(); ++i) {
			pairs.add(new CurrencyPairInfo(VirtualCurrency.BTC, currencyCounterJsonArray.getString(i), null));
		}
	}
}
