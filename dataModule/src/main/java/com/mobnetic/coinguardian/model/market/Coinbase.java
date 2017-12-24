package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Coinbase extends Market {

	private final static String NAME = "Coinbase";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.gdax.com/products/%1$s-%2$s/ticker";
	private final static String URL_CURRENCY_PAIRS = "https://api.gdax.com/products/";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();
	
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.GBP
		});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC
		});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC
		});
	}
	
	public Coinbase() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.last = jsonObject.getDouble("price");
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
		final JSONArray jsonArray = new JSONArray(responseString);
		for (int i = 0; i < jsonArray.length(); ++i) {
			final JSONObject pairJsonObject = jsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					pairJsonObject.getString("base_currency"),
					pairJsonObject.getString("quote_currency"),
					pairJsonObject.getString("id")));
		}
	}
}