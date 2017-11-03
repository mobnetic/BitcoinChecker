package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class Braziliex extends Market {

	private final static String NAME = "Braziliex";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://braziliex.com/api/v1/public/ticker/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://braziliex.com/api/v1/public/ticker";

	public Braziliex() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("highestBid");
		ticker.ask = jsonObject.getDouble("lowestAsk");
		ticker.vol = jsonObject.getDouble("baseVolume24");
		ticker.high = jsonObject.getDouble("highestBid24");
		ticker.low = jsonObject.getDouble("lowestAsk24");
		ticker.last = jsonObject.getDouble("last");
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
		final JSONArray pairIds = jsonObject.names();
		for(int i=0; i<pairIds.length(); ++i) {
			String pairId = pairIds.getString(i);
			if(pairId==null)
				continue;
			String[] currencies = pairId.split("_");
			if(currencies.length!=2)
				continue;

			String currencyBase = currencies[0].toUpperCase(Locale.ENGLISH);
			String currencyCounter = currencies[1].toUpperCase(Locale.ENGLISH);
			pairs.add(new CurrencyPairInfo(currencyBase, currencyCounter, pairId));
		}
	}
}
