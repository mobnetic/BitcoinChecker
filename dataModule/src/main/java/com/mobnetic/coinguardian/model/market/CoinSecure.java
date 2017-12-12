package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CoinSecure extends Market {

	private final static String NAME = "CoinSecure";
	private final static String TTS_NAME = "Coin Secure";
	private final static String URL = "https://api.coinsecure.in/v1/exchange/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.INR
			});
	}
	
	public CoinSecure() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject messageJsonObject = jsonObject.getJSONObject("message");
		ticker.bid = parsePrice(messageJsonObject.getDouble("bid"));
		ticker.ask = parsePrice(messageJsonObject.getDouble("ask"));
		ticker.vol = messageJsonObject.getDouble("coinvolume") / 100000000;
		ticker.high = parsePrice(messageJsonObject.getDouble("high"));
		ticker.low = parsePrice(messageJsonObject.getDouble("low"));
		ticker.last = parsePrice(messageJsonObject.getDouble("lastPrice"));
//		ticker.timestamp = messageJsonObject.getLong("timestamp");
	}
	
	private double parsePrice(double price) {
		return price/100;
	}
}
