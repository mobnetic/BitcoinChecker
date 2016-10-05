package com.mobnetic.coinguardian.model.market;

import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.util.ParseUtils;

public class Cryptonit extends Market {

	private final static String NAME = "Cryptonit";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://cryptonit.net/apiv2/rest/public/ccorder.json?bid_currency=%1$s&ask_currency=%2$s&ticker";
	private final static String URL_CURRENCY_PAIRS = "https://cryptonit.net/apiv2/rest/public/pairs.json";
	
	public Cryptonit() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyCounterLowerCase(), checkerInfo.getCurrencyBaseLowerCase());		// reversed pairs!
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject rateJsonObject = jsonObject.getJSONObject("rate");
		final JSONObject volumeJsonObject = jsonObject.getJSONObject("volume");
		
		ticker.bid = ParseUtils.getDoubleFromString(rateJsonObject, "bid");
		ticker.ask = ParseUtils.getDoubleFromString(rateJsonObject, "ask");
		if(volumeJsonObject.has(checkerInfo.getCurrencyBase())) {
			ticker.vol = ParseUtils.getDoubleFromString(volumeJsonObject, checkerInfo.getCurrencyBase());
		}
		ticker.high = ParseUtils.getDoubleFromString(rateJsonObject, "high");
		ticker.low = ParseUtils.getDoubleFromString(rateJsonObject, "low");
		ticker.last = ParseUtils.getDoubleFromString(rateJsonObject, "last");
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
		final JSONArray pairsJsonArray = new JSONArray(responseString);
		JSONArray currenciesJsonArray = null;
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			currenciesJsonArray = pairsJsonArray.getJSONArray(i);
			if(currenciesJsonArray.length()!=2)
				continue;
			final String currencyBase = currenciesJsonArray.getString(1);		// reversed pairs!
			final String currencyCounter = currenciesJsonArray.getString(0);	// reversed pairs!
			if(currencyBase==null || currencyCounter==null)
				continue;
			
			pairs.add(new CurrencyPairInfo(currencyBase.toUpperCase(Locale.US), currencyCounter.toUpperCase(Locale.US), null));
		}
	}
}
