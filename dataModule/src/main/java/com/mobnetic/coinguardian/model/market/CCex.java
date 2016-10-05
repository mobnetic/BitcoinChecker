package com.mobnetic.coinguardian.model.market;

import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class CCex extends Market {

	public final static String NAME = "C-CEX";
	public final static String TTS_NAME = "C-Cex";
	public final static String URL = "https://c-cex.com/t/%1$s-%2$s.json";
	public final static String URL_CURRENCY_PAIRS = "https://c-cex.com/t/pairs.json";
	
	public CCex() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONObject tickerObject = jsonObject.getJSONObject("ticker");
		
		ticker.bid = tickerObject.getDouble("buy");
		ticker.ask = tickerObject.getDouble("sell");
		ticker.high = tickerObject.getDouble("high");
		ticker.low = tickerObject.getDouble("low");
		ticker.last = tickerObject.getDouble("lastprice");
//		ticker.timestamp = tickerObject.getLong("updated");	// strange date?
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
		final JSONArray pairsJsonArray = jsonObject.getJSONArray("pairs");
		
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			String pair = pairsJsonArray.getString(i);
			if(pair==null)
				continue;
			String[] currencies = pair.split("-", 2);
			if(currencies.length!=2 || currencies[0]==null || currencies[1]==null)
				continue;
			
			pairs.add(new CurrencyPairInfo(currencies[0].toUpperCase(Locale.US), currencies[1].toUpperCase(Locale.US), pair));
		}
	}
}
