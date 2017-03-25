package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitxCom extends Market {

	private final static String NAME = "CoinsBank";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://coinsbank.com/api/public/ticker?pair=%1$s%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.EUR,
				Currency.GBP,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.EUR,
				Currency.GBP,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.GHS, new String[]{
				VirtualCurrency.BTC,
				Currency.EUR,
				Currency.GBP,
				VirtualCurrency.LTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(Currency.EUR, new String[]{
				Currency.GBP,
				Currency.USD
			});
		CURRENCY_PAIRS.put(Currency.GBP, new String[]{
				Currency.USD
			});
	}
	
	public BitxCom() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("data");
		ticker.bid = dataJsonObject.getDouble("buy");
		ticker.ask = dataJsonObject.getDouble("sell");
		ticker.vol = dataJsonObject.getDouble("volume");
		ticker.high = dataJsonObject.getDouble("high");
		ticker.low = dataJsonObject.getDouble("low");
		ticker.last = dataJsonObject.getDouble("last");
	}
}
