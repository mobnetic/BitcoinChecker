package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CoinJar extends Market {

	private final static String NAME = "CoinJar";
	private final static String TTS_NAME = "Coin Jar";
	private final static String URL = "https://coinjar-data.herokuapp.com/fair_rate.json";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.AUD,
				Currency.NZD,
				Currency.CAD,
				Currency.EUR,
				Currency.GBP,
				Currency.SGD,
				Currency.HKD,
				Currency.CHF,
				Currency.JPY
			});
	}
	
	public CoinJar() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final String currencyCounter = checkerInfo.getCurrencyCounter();
		ticker.bid = getPriceFromJsonObject(jsonObject, "bid", currencyCounter);
		ticker.ask = getPriceFromJsonObject(jsonObject, "ask", currencyCounter);
		ticker.last = getPriceFromJsonObject(jsonObject, "spot", currencyCounter);
	}
	
	private double getPriceFromJsonObject(JSONObject jsonObject, String innerObjectName, String currencyCounter) throws Exception {
		JSONObject innerJsonObject = jsonObject.getJSONObject(innerObjectName);
		return innerJsonObject.getDouble(currencyCounter);
	}
}
