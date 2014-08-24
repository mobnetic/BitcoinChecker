package com.mobnetic.coinguardian.model.market;

import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Bter extends Market {

	private final static String NAME = "Bter";
	private final static String TTS_NAME = "B ter";
	private final static String URL = "https://data.bter.com/api/1/ticker/%1$s_%2$s";
	private final static String URL_CURRENCY_PAIRS = "https://data.bter.com/api/1/pairs";

	public Bter() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("buy");
		ticker.ask = jsonObject.getDouble("sell");
		ticker.vol = jsonObject.getDouble("vol_"+checkerInfo.getCurrencyBaseLowerCase());
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
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
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		JSONArray jsonArray = new JSONArray(responseString);
		for(int i=0; i<jsonArray.length(); ++i) {
			String pairId = jsonArray.getString(i);
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
