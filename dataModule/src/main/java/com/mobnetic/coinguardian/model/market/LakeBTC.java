package com.mobnetic.coinguardian.model.market;

import java.util.List;
import java.util.Locale;

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
	private final static String URL = "https://api.lakebtc.com/api_v2/ticker";
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
		final String pairId;
		if (checkerInfo.getCurrencyPairId() == null) {
			pairId = checkerInfo.getCurrencyBaseLowerCase() + checkerInfo.getCurrencyCounterLowerCase();
		} else {
			pairId = checkerInfo.getCurrencyPairId();
		}
		final JSONObject pairJsonObject = jsonObject.getJSONObject(pairId);
		
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
		final JSONArray pairsJsonArray = jsonObject.names();
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			final String pairId = pairsJsonArray.getString(i);
			final String currencyBase = pairId.substring(0, 3).toUpperCase(Locale.ENGLISH);
			final String currencyCounter = pairId.substring(3).toUpperCase(Locale.ENGLISH);
			pairs.add(new CurrencyPairInfo(currencyBase, currencyCounter, pairId));
		}
	}
}
