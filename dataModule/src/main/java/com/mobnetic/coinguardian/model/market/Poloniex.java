package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Poloniex extends Market {

	private final static String NAME = "Poloniex";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://poloniex.com/public?command=returnTicker";
	
	public Poloniex() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject pairJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyCounter()+"_"+checkerInfo.getCurrencyBase());	// Reversed currencies

		ticker.bid = pairJsonObject.getDouble("highestBid");
		ticker.ask = pairJsonObject.getDouble("lowestAsk");
		ticker.vol = pairJsonObject.getDouble("baseVolume");
		ticker.last = pairJsonObject.getDouble("last");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairNames = jsonObject.names();
		
		for(int i=0; i<pairNames.length(); ++i) {
			String pairId = pairNames.getString(i);
			if(pairId==null)
				continue;
			String[] currencies = pairId.split("_");
			if(currencies.length!=2)
				continue;
			
			pairs.add(new CurrencyPairInfo(currencies[1], currencies[0], pairId)); //reversed pairs
		}
	}
}
