package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Justcoin extends Market {

	private final static String NAME = "Justcoin";
	private final static String TTS_NAME = "Just coin";
	private final static String URL = "https://justcoin.com/api/v1/markets";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.EUR,
				VirtualCurrency.LTC,
				Currency.NOK,
				Currency.USD,
				VirtualCurrency.XRP,
				VirtualCurrency.STR
			});
	}
	
	public Justcoin() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONArray jsonArray = new JSONArray(responseString);
		final String idString = checkerInfo.getCurrencyBase()+checkerInfo.getCurrencyCounter();
		for(int i=0; i<jsonArray.length(); ++i) {
			final JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(jsonObject!=null && idString.equals(jsonObject.getString("id"))) {
				parseTickerFromJsonObject(requestId, jsonObject, ticker, checkerInfo);
				return;
			}
		}
		
		throw new IllegalArgumentException();
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
	}
}
