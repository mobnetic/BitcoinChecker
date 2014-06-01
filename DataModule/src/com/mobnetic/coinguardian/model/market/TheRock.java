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

public class TheRock extends Market {

	private final static String NAME = "TheRock";
	private final static String TTS_NAME = "The Rock";
	private final static String URL = "https://www.therocktrading.com/api/ticker/%1$s%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.EUR,
				Currency.USD,
				VirtualCurrency.XRP
			});
		CURRENCY_PAIRS.put(Currency.EUR, new String[]{
				VirtualCurrency.DOGE,
				VirtualCurrency.XRP
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.EUR,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				Currency.EUR
			});
		CURRENCY_PAIRS.put(Currency.USD, new String[]{
				VirtualCurrency.XRP
			});
	}
	
	public TheRock() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONArray resultArray = jsonObject.getJSONArray("result");
		final JSONObject tickerObject = resultArray.getJSONObject(0);
		
		ticker.bid = tickerObject.getDouble("bid");
		ticker.ask = tickerObject.getDouble("ask");
		ticker.vol = tickerObject.getDouble("volume");
		ticker.high = tickerObject.getDouble("high");
		ticker.low = tickerObject.getDouble("low");
		ticker.last = tickerObject.getDouble("last");
	}
}
