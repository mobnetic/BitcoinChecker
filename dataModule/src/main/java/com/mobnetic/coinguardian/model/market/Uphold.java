// @joseccnet contribution.
package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.util.ParseUtils;

public class Uphold extends Market {

	private final static String NAME = "Uphold";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.uphold.com/v0/ticker/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.uphold.com/v0/ticker";
	
	public Uphold() {
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
		ticker.last = ((ticker.bid+ticker.ask)/2); //This is how Uphold operate on production (as I observed)
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
		
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			final JSONObject pairJsonObject = pairsJsonArray.getJSONObject(i);
			final String pairId = pairJsonObject.getString("pair");
			final String currencyCounter = pairJsonObject.getString("currency");
			if (currencyCounter != null && pairId != null && pairId.endsWith(currencyCounter)) {
				final String currencyBase = pairId.substring(0, pairId.length() - currencyCounter.length());
				if (!currencyCounter.equals(currencyBase)) {
					// normal pair
					pairs.add(new CurrencyPairInfo(
							currencyBase,
							currencyCounter,
							pairId));
					// reversed pair
					pairs.add(new CurrencyPairInfo(
							currencyCounter,
							currencyBase,
							currencyCounter+currencyBase));
				}
			}
		}
	}
}
