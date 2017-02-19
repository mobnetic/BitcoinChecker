package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.config.Settings;
import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Liqui extends Market {

	//Liqui's API is similar to BTC-e, so use BTCe.java for any help with updating this code
	private final static String NAME = "Liqui.io";
	private final static String TTS_NAME = "Liqui";
	private final static String URL = " https://api.liqui.io/api/3/ticker/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.liqui.io/api/3/info";

	public Liqui() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		String pairId = checkerInfo.getCurrencyPairId();
		if(checkerInfo.getCurrencyPairId() == null) {
			pairId = String.format("%1$s_%2$s", checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
		}
		return String.format(URL, pairId);
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONArray names = jsonObject.names();
		JSONObject tickerJsonObject = jsonObject.getJSONObject(names.getString(0));
		ticker.bid = tickerJsonObject.getDouble("sell");	// REVERSED!
		ticker.ask = tickerJsonObject.getDouble("buy");	// REVERSED!
		ticker.vol = tickerJsonObject.getDouble("vol");
		ticker.high = tickerJsonObject.getDouble("high");
		ticker.low = tickerJsonObject.getDouble("low");
		ticker.last = tickerJsonObject.getDouble("last");
		ticker.timestamp = tickerJsonObject.getLong("updated");
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
		final JSONObject pairsJsonObject = jsonObject.getJSONObject("pairs");
		final JSONArray pairsNames = pairsJsonObject.names();
		for(int i=0; i<pairsNames.length(); ++i) {
			String pairId = pairsNames.getString(i);
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