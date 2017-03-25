package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CexIO extends Market {

	private final static String NAME = "CEX.IO";
	private final static String TTS_NAME = "CEX IO";
	private final static String URL = "https://cex.io/api/ticker/%1$s/%2$s";
	private final static String URL_CURRENCY_PAIRS = "https://cex.io/api/currency_limits";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.GBP,
				Currency.RUB,
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				Currency.USD,
				Currency.EUR,	
				VirtualCurrency.BTC		
			});
		CURRENCY_PAIRS.put(VirtualCurrency.GHS, new String[]{
				VirtualCurrency.BTC,
			});
	}
	
	public CexIO() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		if (jsonObject.has("bid")) {
			ticker.bid = jsonObject.getDouble("bid");
		}
		if (jsonObject.has("ask")) {
			ticker.ask = jsonObject.getDouble("ask");
		}
		ticker.vol = jsonObject.getDouble("volume");
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
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("data");
		final JSONArray pairsJsonArray = dataJsonObject.getJSONArray("pairs");
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			final JSONObject pairJsonObject = pairsJsonArray.getJSONObject(i);
			final String currencyBase = pairJsonObject.getString("symbol1");
			final String currencyCounter = pairJsonObject.getString("symbol2");
			if(currencyBase != null && currencyCounter != null) {
				pairs.add(new CurrencyPairInfo(
						currencyBase,
						currencyCounter,
						null));
			}
		}
	}
}
