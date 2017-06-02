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

public class TheRock extends Market {

	private final static String NAME = "TheRock";
	private final static String TTS_NAME = "The Rock";
	private final static String URL = "https://api.therocktrading.com/v1/funds/%1$s/ticker";
	private final static String URL_CURRENCY_PAIRS = "https://api.therocktrading.com/v1/funds";
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
		String pairId = checkerInfo.getCurrencyPairId();
		if (pairId == null) {
			pairId = fixCurrency(checkerInfo.getCurrencyBase()) + fixCurrency(checkerInfo.getCurrencyCounter());
		}
		return String.format(URL, pairId);
	}
	
	private String fixCurrency(String currency) {
		if(VirtualCurrency.DOGE.equals(currency)) {
			return VirtualCurrency.DOG;
		}
		return currency;
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

	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray fundsJsonArray = jsonObject.getJSONArray("funds");
		for (int i = 0; i < fundsJsonArray.length(); i++) {
			final JSONObject pairJsonObject = fundsJsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					pairJsonObject.getString("trade_currency"),
					pairJsonObject.getString("base_currency"),
					pairJsonObject.getString("id"))
			);
		}
	}
}
