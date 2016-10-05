package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.util.ParseUtils;

public class HitBtc extends Market {

	private final static String NAME = "HitBTC";
	private final static String TTS_NAME = "Hit BTC";
	private final static String URL = "https://api.hitbtc.com/api/1/public/%1$s/ticker";
	private final static String URL_CURRENCY_PAIRS = "https://api.hitbtc.com/api/1/public/symbols";
	
	public HitBtc() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = ParseUtils.getDoubleFromString(jsonObject, "bid");
		ticker.ask = ParseUtils.getDoubleFromString(jsonObject, "ask");
		ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume");
		ticker.high = ParseUtils.getDoubleFromString(jsonObject, "high");
		ticker.low = ParseUtils.getDoubleFromString(jsonObject, "low");
		ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last");
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
		final JSONArray symbolsJsonArray = jsonObject.getJSONArray("symbols");
		for(int i=0; i<symbolsJsonArray.length(); ++i) {
			final JSONObject pairJsonObject = symbolsJsonArray.getJSONObject(i);
			
			final String currencyBase = pairJsonObject.getString("commodity");
			final String currencyCounter = pairJsonObject.getString("currency");
			final String currencyPairId = pairJsonObject.getString("symbol");
			if(currencyBase==null || currencyCounter==null || currencyPairId==null)
				continue;
			
			pairs.add(new CurrencyPairInfo(currencyBase, currencyCounter, currencyPairId));
		}
	}
}
