package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Justcoin extends Market {

	private final static String NAME = "Justcoin";
	private final static String TTS_NAME = "Just coin";
	private final static String URL = "https://justcoin.com/api/2/%1$s%2$s/money/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.GBP,
				Currency.HKD,
				Currency.CHF,
				Currency.AUD,
				Currency.CAD,
				Currency.NZD,
				Currency.SGD,
				Currency.JPY
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.STR, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
				VirtualCurrency.BTC
			});
	}
	
	public Justcoin() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataObject = jsonObject.getJSONObject("data");
		
		ticker.bid = getPriceValueFromObject(dataObject, "buy");
		ticker.ask = getPriceValueFromObject(dataObject, "sell");
		ticker.vol = getPriceValueFromObject(dataObject, "vol");
		ticker.high = getPriceValueFromObject(dataObject, "high");
		ticker.low = getPriceValueFromObject(dataObject, "low");
		ticker.last = getPriceValueFromObject(dataObject, "last");
	}
	
	private double getPriceValueFromObject(JSONObject jsonObject, String key) throws Exception {
		final JSONObject innerObject = jsonObject.getJSONObject(key);
		return innerObject.getDouble("value");
	}
}
