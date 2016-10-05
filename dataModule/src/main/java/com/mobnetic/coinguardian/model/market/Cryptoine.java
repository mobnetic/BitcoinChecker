package com.mobnetic.coinguardian.model.market;

import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Cryptoine extends Market {

	private final static String NAME = "Cryptoine";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://cryptoine.com/api/1/ticker/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://cryptoine.com/api/1/markets";
	
	public Cryptoine() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = getDoubleOrZero(jsonObject, "buy");
		ticker.ask = getDoubleOrZero(jsonObject, "sell");
		ticker.vol = getDoubleOrZero(jsonObject, "vol_exchange");
		ticker.high = getDoubleOrZero(jsonObject, "high");
		ticker.low = getDoubleOrZero(jsonObject, "low");
		ticker.last = getDoubleOrZero(jsonObject, "last");
	}
	
	private double getDoubleOrZero(JSONObject jsonObject, String name) throws Exception {
		if(jsonObject.isNull(name))
			return 0;
		return jsonObject.getDouble(name);
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
		final JSONObject dataJsonObject = jsonObject.getJSONObject("data");
		final JSONArray pairNames = dataJsonObject.names();
		
		for(int i=0; i<pairNames.length(); ++i) {
			String pairId = pairNames.getString(i);
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
